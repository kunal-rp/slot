package com.bazelgrpc.demo.annotate;

import io.grpc.stub.StreamObserver;
import com.bazelgrpc.demo.annotate.AnnotateManagementGrpc;
import com.bazelgrpc.demo.annotate.AnnotateServiceProto.GetAnnotatePostRequest;
import com.bazelgrpc.demo.annotate.AnnotateServiceProto.GetAnnotatePostResponse;

public class AnnotateManagementImpl extends AnnotateManagementGrpc.AnnotateManagementImplBase {

    @Override
    public void getAnnotatePost(GetAnnotatePostRequest req,
            StreamObserver<GetAnnotatePostResponse> responseObserver) {
        System.out.println("getAnnotatePost");
        responseObserver.onNext(GetAnnotatePostResponse.newBuilder()
                .addAnnotatePosts(AnnotatePostUtil.createTestingAnnotatePost()).build());
        responseObserver.onCompleted();
    }

}
