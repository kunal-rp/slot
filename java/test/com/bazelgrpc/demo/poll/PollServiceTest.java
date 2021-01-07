package com.bazelgrpc.demo.poll;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import io.grpc.ManagedChannel;
import io.grpc.testing.GrpcServerRule;
import com.bazelgrpc.demo.poll.PollManagementImpl;
import com.bazelgrpc.demo.poll.PollManagementGrpc;
import com.bazelgrpc.demo.poll.PollServiceProto.GetPollRequest;
import com.bazelgrpc.demo.poll.PollServiceProto.GetPollResponse;

/**
 * Poll Testing
 */
public class PollServiceTest {

    private final int TEST_POLL_ID = 200;

    @Rule
    public final GrpcServerRule grpcServerRule = new GrpcServerRule().directExecutor();

    @Test
    public void basicTest() throws Exception {
        // Add the service to the in-process server.
        grpcServerRule.getServiceRegistry().addService(new PollManagementImpl());
        PollManagementGrpc.PollManagementBlockingStub blockingStub =
                PollManagementGrpc.newBlockingStub(grpcServerRule.getChannel());
        GetPollResponse response = blockingStub.getPolls(createPollRequest());

        assertEquals(response.getPollsCount(), 1);

    }

    private GetPollRequest createPollRequest() {
        return GetPollRequest.newBuilder().setPollId(TEST_POLL_ID).build();

    }

}
