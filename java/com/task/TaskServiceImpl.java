package com.task;

import java.util.List;
import java.util.Optional;
import com.google.inject.Inject;
import com.google.rpc.Status;
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

        try {
            responseObserver.onNext(
            	GenerateScheduleResponse.newBuilder()
            		.addAllGeneratedTaskEntry(
            			scheduleUtil.generateSchedule(
            				taskDBHandler.fetchTemplatesForTimeslot(startTime, endTime).get(),
            				startTime,
            				endTime))
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