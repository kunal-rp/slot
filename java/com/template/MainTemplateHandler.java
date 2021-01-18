package com.template;

import io.grpc.ManagedChannelBuilder;
import com.bazelgrpc.demo.util.SetupUtil;
import com.template.TemplateServiceGrpc;
import com.template.TemplateServiceProto.GenerateScheduleRequest;
import com.template.TemplateServiceProto.GenerateScheduleResponse;

/* Prod implementation of Template Service Handler  */ 
public class MainTemplateHandler {

	TemplateServiceGrpc.TemplateServiceBlockingStub blockingStub =
                TemplateServiceGrpc.newBlockingStub(
                	ManagedChannelBuilder.forTarget("127.0.0.1")
               	.usePlaintext().build());

    public GenerateScheduleResponse generateSchedule(GenerateScheduleRequest request){
    	 
    	 return blockingStub.generateScheduleFromTemplates(request);

    }

}
