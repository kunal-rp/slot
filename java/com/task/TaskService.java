package com.task;

import com.util.ServiceModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Guice;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import com.util.SetupUtil;
import com.task.template.GenerativeScheduleUtil;
import com.task.db.TaskDBHandler;
import com.task.TaskServiceProto.GenerateScheduleRequest;
import com.task.TaskServiceProto.GenerateScheduleResponse;

public class TaskService {

    public static void main(String[] args) throws Exception {

        Injector injector = Guice.createInjector(new ServiceModule());

        Server pollServer = 
            ServerBuilder
                .forPort(SetupUtil.DEFAULT_SERVICE_PORT)
                .addService(injector.getInstance(TaskServiceImpl.class))
            .build();
        pollServer.start();
        System.out.println("Task Service Started!!");
        pollServer.awaitTermination();
    }
}


class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {

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

        responseObserver.onNext(
        	GenerateScheduleResponse.newBuilder()
        		.addAllGeneratedTaskEntries(
        			scheduleUtil.generateSchedule(
        				taskDBHandler.fetchTemplatesForTimeslot(startTime, endTime),
        				startTime,
        				endTime))
        		.build());
        responseObserver.onCompleted();
    }

}