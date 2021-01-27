package com.task;

import com.google.inject.Inject;
import io.grpc.stub.StreamObserver;
import com.task.template.GenerativeScheduleUtil;
import com.task.db.TaskDBHandler;
import com.task.TaskServiceProto.GenerateScheduleRequest;
import com.task.TaskServiceProto.GenerateScheduleResponse;

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

        responseObserver.onNext(
        	GenerateScheduleResponse.newBuilder()
        		.addAllGeneratedTaskEntry(
        			scheduleUtil.generateSchedule(
        				taskDBHandler.fetchTemplatesForTimeslot(startTime, endTime),
        				startTime,
        				endTime))
        		.build());
        responseObserver.onCompleted();
    }

}