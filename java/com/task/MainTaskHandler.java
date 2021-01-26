package com.task;

import io.grpc.ManagedChannelBuilder;
import com.util.SetupUtil;
import com.task.TaskServiceGrpc;
import com.task.TaskServiceProto.GenerateScheduleRequest;
import com.task.TaskServiceProto.GenerateScheduleResponse;

/* Prod implementation of Task Service Handler  */ 
public class MainTaskHandler {

	TaskServiceGrpc.TaskServiceBlockingStub blockingStub =
                TaskServiceGrpc.newBlockingStub(
                	ManagedChannelBuilder.forTarget("127.0.0.1")
               	.usePlaintext().build());

    public GenerateScheduleResponse generateSchedule(GenerateScheduleRequest request){
    	 
    	 return blockingStub.generateSchedule(request);

    }

}
