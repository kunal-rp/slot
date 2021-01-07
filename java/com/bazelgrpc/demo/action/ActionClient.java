package com.bazelgrpc.demo.action;

import io.grpc.ManagedChannelBuilder;
import com.bazelgrpc.demo.action.ActionManagementGrpc;
import com.bazelgrpc.demo.action.ActionServiceProto.InitializePageRequest;
import com.bazelgrpc.demo.action.ActionServiceProto.InitializePageResponse;


/* client that will be used to call the action service when it's run locally/in cloud */
public class ActionClient {

        private static final String URL = "https://www.simbasurfing.com";

        public static void main(String[] args) {
                long start = System.currentTimeMillis();
                ActionManagementGrpc.ActionManagementBlockingStub blockingStub =
                                ActionManagementGrpc.newBlockingStub(ManagedChannelBuilder
                                                .forTarget(URL).usePlaintext().build());
                InitializePageResponse response = blockingStub
                                .initializePage(InitializePageRequest.getDefaultInstance());
                System.out.println("first :");
                System.out.println(System.currentTimeMillis() - start);
                start = System.currentTimeMillis();
                response = blockingStub.initializePage(InitializePageRequest.getDefaultInstance());
                System.out.println(response);
                System.out.println(System.currentTimeMillis() - start);
        }
}

