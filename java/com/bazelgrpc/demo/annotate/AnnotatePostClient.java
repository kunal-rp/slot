package com.bazelgrpc.demo.annotate;

import io.grpc.ManagedChannelBuilder;
import com.bazelgrpc.demo.util.SetupUtil;
import com.bazelgrpc.demo.annotate.AnnotateManagementGrpc;
import com.bazelgrpc.demo.annotate.AnnotateServiceProto.GetAnnotatePostRequest;
import com.bazelgrpc.demo.annotate.AnnotateServiceProto.GetAnnotatePostResponse;

public class AnnotatePostClient {

    public static void main(String[] args) {
        AnnotateManagementGrpc.AnnotateManagementBlockingStub blockingStub =
                AnnotateManagementGrpc.newBlockingStub(ManagedChannelBuilder
                        .forTarget("some dummy value for now ").usePlaintext().build());
        GetAnnotatePostResponse response =
                blockingStub.getAnnotatePost(GetAnnotatePostRequest.getDefaultInstance());
    }
}

