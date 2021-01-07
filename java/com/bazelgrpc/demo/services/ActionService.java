package com.bazelgrpc.demo.services;

import com.bazelgrpc.demo.action.ActionManagementImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.bazelgrpc.demo.util.SetupUtil;
import com.google.inject.Injector;
import com.google.inject.Guice;


public class ActionService {

    public static void main(String[] args) throws Exception {

        Injector injector = Guice.createInjector(new ServiceModule());

        Server pollServer = ServerBuilder.forPort(SetupUtil.DEFAULT_SERVICE_PORT)
                .addService(injector.getInstance(ActionManagementImpl.class)).build();
        pollServer.start();
        System.out.println("BGRPC Action Server Started!!");
        pollServer.awaitTermination();
    }
}
