package com.task;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream; 
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import com.google.inject.Inject;
import com.google.rpc.Status;
import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.protobuf.StatusProto;
import com.google.rpc.Code;
import io.grpc.stub.StreamObserver;
import com.task.template.GenerativeScheduleUtil;
import com.task.db.TaskDBHandler;
import com.task.TaskServiceProto.GenerateScheduleRequest;
import com.task.TaskServiceProto.GenerateScheduleResponse;
import com.task.TaskServiceProto.CreateNewTemplatesRequest;
import com.task.TaskServiceProto.CreateNewTemplatesResponse;
import com.task.TaskServiceProto.UpdateTemplateRequest;
import com.task.TaskServiceProto.UpdateTemplateResponse;
import com.task.TaskProto.TaskEntry;
import com.task.TaskProto.TaskTemplate;
import com.task.TaskDBProto.DBFetchTemplateRequest;
import com.task.TaskDBProto.DBFetchEntriesRequest;
import com.task.TaskDBProto.TemplateId;

public class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {

    private TaskDBHandler taskDBHandler;
    private GenerativeScheduleUtil scheduleUtil;

    @Inject
    public TaskServiceImpl(TaskDBHandler taskDBHandler,GenerativeScheduleUtil scheduleUtil ){
        this.taskDBHandler = taskDBHandler;
        this.scheduleUtil = scheduleUtil;
    }



    @Override
    public void generateSchedule(GenerateScheduleRequest req,
            StreamObserver<GenerateScheduleResponse> responseObserver) {

        System.out.println("generateSchedule");

        Executor executor = MoreExecutors.newDirectExecutorService();

        int startTime = (int) req.getScheduleStartUnix();
        int endTime = (int) req.getScheduleEndUnix();

        // First get all templates that fall in timeslot
        ListenableFuture<List<TaskEntry>> generatedEntriesFuture = getGeneratedEntriesFromTemplates(taskDBHandler.fetchTemplates(
            DBFetchTemplateRequest.newBuilder()
                .setStartingUnix(startTime)
                .setEndingUnix(endTime)
            .build()), startTime, endTime, executor);

        // Get Corresponding entries to templates ( generated and existing) that fall in timeslot
        ListenableFuture<List<TaskEntry>> templateRelatedEntries = 
            getGeneratedAndCorrespondingEntriesFromTemplates(generatedEntriesFuture , startTime, endTime, executor);
        // Get Non corresponding entries to templates that fall in timeslot 
        ListenableFuture<List<TaskEntry>> nonCorrespondingEntries = 
            getNonCorrespondingEntriesFromTemplatesInTimeslot(generatedEntriesFuture , startTime, endTime, executor);
            
           Callable<List<TaskEntry>> combineEntryCallable =
               new Callable<List<TaskEntry>>() {
                 public List<TaskEntry> call() throws Exception {
                    return 
                        Stream.concat(
                            templateRelatedEntries.get().stream(),
                            nonCorrespondingEntries.get().stream())
                        .collect(toList());
                 }
               };
           ListenableFuture<List<TaskEntry>> taskEntriesFuture =
               Futures.whenAllSucceed(templateRelatedEntries, nonCorrespondingEntries)
                   .call(combineEntryCallable, executor);

        try {
            responseObserver.onNext(
            	GenerateScheduleResponse.newBuilder()
            		.addAllGeneratedTaskEntry(
            				taskEntriesFuture.get())
            		.build());
        }catch(Exception e){
            Status status = Status.newBuilder()
                .setCode(Code.UNKNOWN.getNumber())
                .setMessage("something bad happened")
                .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));

        }
        responseObserver.onCompleted();
    }

    private ListenableFuture<List<TaskEntry>> getGeneratedEntriesFromTemplates(ListenableFuture<List<TaskTemplate>> templateFuture,int startTime, int endTime,Executor executor){
         Function<List<TaskTemplate>, List<TaskEntry>> createGeneratedEntriesFuture =
          new Function<List<TaskTemplate>, List<TaskEntry>>() {
            public List<TaskEntry> apply(List<TaskTemplate> templates) {
                return scheduleUtil.generateSchedule(
                    templates,
                    startTime,
                    endTime);
                }
          };
        return Futures.transform(templateFuture, createGeneratedEntriesFuture, executor);
    }

    private ListenableFuture<List<TaskEntry>> getGeneratedAndCorrespondingEntriesFromTemplates(ListenableFuture<List<TaskEntry>> entryFuture,int startTime, int endTime,Executor executor){
        AsyncFunction<List<TaskEntry>, List<TaskEntry>> queryEntriesFunction =
          new AsyncFunction<List<TaskEntry>, List<TaskEntry>>() {
            public ListenableFuture<List<TaskEntry>> apply(List<TaskEntry> entries) {
              return taskDBHandler.fetchEntries(
                DBFetchEntriesRequest.newBuilder()
                    .setIncludeTimeAlterations(false)
                    .addAllValidTemplateId(getGeneratedEntryTemplateIds(entries, startTime, endTime))
                .build());
                }
          };
        ListenableFuture<List<TaskEntry>> correspondingEntriesFuture = Futures.transformAsync(entryFuture, queryEntriesFunction, executor);
        return filterOutExistingEntriesAndCombineGeneratedWithExisting(entryFuture,correspondingEntriesFuture, startTime, endTime, executor );
    }

    private ListenableFuture<List<TaskEntry>> getNonCorrespondingEntriesFromTemplatesInTimeslot(ListenableFuture<List<TaskEntry>> entryFuture,int startTime, int endTime,Executor executor){
        AsyncFunction<List<TaskEntry>, List<TaskEntry>> queryEntriesFunction =
          new AsyncFunction<List<TaskEntry>, List<TaskEntry>>() {
            public ListenableFuture<List<TaskEntry>> apply(List<TaskEntry> entries) {
              return taskDBHandler.fetchEntries(
                DBFetchEntriesRequest.newBuilder()
                    .setIncludeTimeAlterations(true)
                    .setStartingUnix(startTime)
                    .setEndingUnix(endTime)
                    .addAllInvalidTemplateId(getGeneratedEntryTemplateIds(entries, startTime, endTime))
                .build());
                }
          };
        return Futures.transformAsync(entryFuture, queryEntriesFunction, executor);
    } 

    private List<TemplateId> getGeneratedEntryTemplateIds(List<TaskEntry> entries, int startTime, int endTime){
        return entries.stream().map(entry -> 
                TemplateId.newBuilder()
                    .setTemplateId(entry.getTemplateId())
                    .setOccurance(entry.getOccurance())
                    .build()
            ).collect(toList());
    }

    // need to filter out all generated entries that already have existing entry
    // then combine generated entries and existing entries
    // then filter any entries that don't fall in timeslot
    private ListenableFuture<List<TaskEntry>> filterOutExistingEntriesAndCombineGeneratedWithExisting(
        ListenableFuture<List<TaskEntry>> generatedEntriesFuture,
        ListenableFuture<List<TaskEntry>> correspondingEntriesFuture, 
        int startTime,
        int endTime,
        Executor executor){

           Callable<List<TaskEntry>> filterAndCombineCallable =
               new Callable<List<TaskEntry>>() {
                 public List<TaskEntry> call() throws Exception {
                    List<TaskEntry> correspondingEntries = correspondingEntriesFuture.get();
                    return 
                        Stream.concat(
                            correspondingEntriesFuture.get().stream(),
                            generatedEntriesFuture.get().stream()
                                .filter(entry -> 
                                    !correspondingEntries.stream().filter(
                                        corrEntry -> entry.getTemplateId() == corrEntry.getTemplateId() && entry.getOccurance() == corrEntry.getOccurance())
                                        .findAny()
                                    .isPresent()))
                        .collect(toList())
                        .stream()
                            .filter(entry -> entryFallsInTimeslot(entry, startTime, endTime))
                        .collect(toList());
                 }
               };
            return Futures.whenAllSucceed(generatedEntriesFuture, correspondingEntriesFuture)
                   .call(filterAndCombineCallable, executor);
    }

    private boolean entryFallsInTimeslot(TaskEntry entry, int startTime, int endTime){
        long entryStartTime = entry.getStartTimestamp() + entry.getTimeAlterations().getStartTimestampSurplus();
        long entryEndTime = entryStartTime + entry.getDuration() + entry.getTimeAlterations().getDurationSurplus();

        return ((int) entryStartTime) <= endTime && ((int) entryEndTime) >= startTime;
    }

    @Override
    public void createNewTemplates(CreateNewTemplatesRequest req, StreamObserver<CreateNewTemplatesResponse> responseObserver){

         try {
            responseObserver.onNext(
                CreateNewTemplatesResponse.newBuilder()
                    .addAllCreatedTemplates(
                        taskDBHandler.insertNewTemplates(req.getTemplatesToAddList()).get())
                    .build());
        }catch(Exception e){
            Status status = Status.newBuilder()
                .setCode(Code.UNKNOWN.getNumber())
                .setMessage("something bad happened")
                .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));

        }
        responseObserver.onCompleted();
    }

     @Override
    public void updateTemplate(UpdateTemplateRequest req, StreamObserver<UpdateTemplateResponse> responseObserver){
         try {
            ListenableFuture<Void> updateTemplateFuture =
                taskDBHandler.alterTemplate(
                    req.getTemplateId(),
                    (req.getEndTimestamp() == 0 ? Optional.empty() : Optional.of(req.getEndTimestamp())),
                    (req.getDescription().isEmpty() ? Optional.empty() : Optional.of(req.getDescription())),
                    (req.getDataCollectionIdList().isEmpty() ? Optional.empty() : Optional.of(req.getDataCollectionIdList())));

              updateTemplateFuture.addListener(new Runnable() {
                    @Override
                    public void run() {
                            responseObserver.onNext(UpdateTemplateResponse.getDefaultInstance());
                            responseObserver.onCompleted();
                    }
                }, MoreExecutors.newDirectExecutorService());
        }catch(Exception e){
            Status status = Status.newBuilder()
                .setCode(Code.UNKNOWN.getNumber())
                .setMessage("something bad happened")
                .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            responseObserver.onCompleted();
        }
    }

}