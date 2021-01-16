package com.template;

import io.grpc.ManagedChannelBuilder;
import com.bazelgrpc.demo.util.SetupUtil;
import com.template.TemplateServiceGrpc;
import com.template.TemplateServiceProto.GenerateScheduleRequest;
import com.template.TemplateServiceProto.GenerateScheduleResponse;

public class TemplateClient {


    public static void main(String[] args) {

		TemplateServiceGrpc.TemplateServiceBlockingStub blockingStub =
                TemplateServiceGrpc.newBlockingStub(
                	ManagedChannelBuilder.forTarget("127.0.0.1")
               	.usePlaintext().build());

        GenerateScheduleResponse response =
                blockingStub.generateScheduleFromTemplates(
                	GenerateScheduleRequest.newBuilder()
                    .setScheduleStartUnix(1610308800)
                    .setScheduleEndUnix(1610388800)
                    .build());
        System.out.println(response.getGeneratedTaskEntriesList());
    }
}

