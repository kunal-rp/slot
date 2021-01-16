package com.template;

import com.template.TemplateServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.bazelgrpc.demo.util.SetupUtil;

public class TemplateService {

    public static void main(String[] args) throws Exception {

        Server pollServer = ServerBuilder.forPort(SetupUtil.DEFAULT_SERVICE_PORT)
                .addService(new TemplateServiceImpl()).build();
        pollServer.start();
        System.out.println("Template Started!!");
        pollServer.awaitTermination();
    }
}
