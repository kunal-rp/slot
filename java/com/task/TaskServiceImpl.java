package com.task;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream; 
import java.util.concurrent.Callable;
import com.google.inject.Inject;
import com.google.rpc.Status;
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

        int startTime = (int) req.getScheduleStartUnix();
        int endTime = (int) req.getScheduleEndUnix();

        ListenableFuture<List<TaskTemplate>> fetchTemplatesFuture = taskDBHandler.fetchTemplates(DBFetchTemplateRequest.newBuilder().setStartingUnix(startTime).setEndingUnix(endTime).build());
        ListenableFuture<List<TaskEntry>> fetchEntryFuture = taskDBHandler.fetchEntries(
            DBFetchEntriesRequest.newBuilder()
                .setIncludeTimeAlterations(true)
                .setStartingUnix(startTime)
                .setEndingUnix(endTime)
            .build());

        //TODO : update do correct fetching of templates & entries
        // for now, return genreated entries and existing entries from timeslot 
        // https://guava.dev/releases/20.0/api/docs/com/google/common/util/concurrent/Futures.FutureCombiner.html

           Callable<List<TaskEntry>> combineEntryCallable =
               new Callable<List<TaskEntry>>() {
                 public List<TaskEntry> call() throws Exception {
                    return 
                        Stream.concat(
                            scheduleUtil.generateSchedule(
                                fetchTemplatesFuture.get(),
                                startTime,
                                endTime
                            ).stream(),
                            fetchEntryFuture.get().stream())
                        .collect(toList());
                 }
               };
           ListenableFuture<List<TaskEntry>> combinedTasks =
               Futures.whenAllSucceed(fetchTemplatesFuture, fetchEntryFuture)
                   .call(combineEntryCallable, MoreExecutors.newDirectExecutorService());

        try {
            responseObserver.onNext(
            	GenerateScheduleResponse.newBuilder()
            		.addAllGeneratedTaskEntry(
            				combinedTasks.get())
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