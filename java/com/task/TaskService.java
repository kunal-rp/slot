package com.task;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import com.util.SetupUtil;
import com.template.GenerativeScheduleUtil;
import com.task.TaskServiceProto.GenerateScheduleRequest;
import com.task.TaskServiceProto.GenerateScheduleResponse;

public class TaskService {

    public static void main(String[] args) throws Exception {

        Server pollServer = ServerBuilder.forPort(SetupUtil.DEFAULT_SERVICE_PORT)
                .addService(new TaskServiceImpl()).build();
        pollServer.start();
        System.out.println("Task Service Started!!");
        pollServer.awaitTermination();
    }
}


class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {

    @Override
    public void generateSchedule(GenerateScheduleRequest req,
            StreamObserver<GenerateScheduleResponse> responseObserver) {

    	GenerativeScheduleUtil scheduleUtil = new GenerativeScheduleUtil();

        responseObserver.onNext(
        	GenerateScheduleResponse.newBuilder()
        		.addAllGeneratedTaskEntries(
        			scheduleUtil.generateSchedule(
        				SampleTasksUtil.PROD_TEMPLATES,
        				(int) req.getScheduleStartUnix(),
        				(int) req.getScheduleEndUnix()))
        		.build());
        responseObserver.onCompleted();
    }

}