package com.bazelgrpc.demo.annotate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import io.grpc.ManagedChannel;
import io.grpc.testing.GrpcServerRule;
import com.bazelgrpc.demo.annotate.AnnotateManagementImpl;
import com.bazelgrpc.demo.annotate.AnnotateManagementGrpc;
import com.bazelgrpc.demo.annotate.AnnotateServiceProto.GetAnnotatePostRequest;
import com.bazelgrpc.demo.annotate.AnnotateServiceProto.GetAnnotatePostResponse;

/**
 * Annotate Testing
 */
public class AnnotatePostServiceTest {

    private final int TEST_POLL_ID = 200;

    @Rule
    public final GrpcServerRule grpcServerRule = new GrpcServerRule().directExecutor();

    @Test
    public void basicTest() throws Exception {
        // Add the service to the in-process server.
        grpcServerRule.getServiceRegistry().addService(new AnnotateManagementImpl());
        AnnotateManagementGrpc.AnnotateManagementBlockingStub blockingStub =
                AnnotateManagementGrpc.newBlockingStub(grpcServerRule.getChannel());
        GetAnnotatePostResponse response = blockingStub.getAnnotatePost(createAnnotateRequest());

        assertEquals(response.getAnnotatePostsCount(), 1);

    }

    private GetAnnotatePostRequest createAnnotateRequest() {
        return GetAnnotatePostRequest.getDefaultInstance();

    }

}
