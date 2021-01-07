package com.bazelgrpc.demo.services;

import com.bazelgrpc.demo.poll.PollManagementImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.bazelgrpc.demo.util.SetupUtil;


public class PollService {

        public static void main(String[] args) throws Exception {

                Server pollServer = ServerBuilder.forPort(SetupUtil.DEFAULT_SERVICE_PORT)
                                .addService(new PollManagementImpl()).build();
                pollServer.start();
                System.out.println("BGRPC Poll Server Started!");
                pollServer.awaitTermination();
        }
}
