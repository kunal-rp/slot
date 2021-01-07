package com.bazelgrpc.demo.action;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import io.grpc.ManagedChannel;
import com.bazelgrpc.demo.poll.PollProto.Poll;
import io.grpc.testing.GrpcServerRule;
import com.bazelgrpc.demo.poll.PollHandler;
import com.bazelgrpc.demo.poll.FakePollHandler;
import com.bazelgrpc.demo.action.ActionManagementImpl;
import com.bazelgrpc.demo.action.ActionManagementGrpc;
import com.bazelgrpc.demo.action.ActionServiceProto.InitializePageRequest;
import com.bazelgrpc.demo.action.ActionServiceProto.InitializePageResponse;
import com.bazelgrpc.demo.poll.FakePollHandler;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Guice;
import com.google.inject.util.Providers;
import com.google.inject.AbstractModule;

/**
 * Action Service Testing
 */
public class ActionServiceTest {

    private final Poll EXAMPLE_POLL = Poll.newBuilder().setPollId(18900).build();

    protected Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(PollHandler.class).to(FakePollHandler.class);
        }
    });

    @Rule
    public final GrpcServerRule grpcServerRule = new GrpcServerRule().directExecutor();

    @Before
    public void setup() {
        injector.injectMembers(this);
    }

    @After
    public void cleanUp() {
        fakePollHandler.reset();
    }

    @Inject
    private FakePollHandler fakePollHandler;


    @Test
    public void basicTest() throws Exception {
        fakePollHandler.addPoll(EXAMPLE_POLL);
        // Add the service to the in-process server.
        grpcServerRule.getServiceRegistry()
                .addService(injector.getInstance(ActionManagementImpl.class));
        ActionManagementGrpc.ActionManagementBlockingStub blockingStub =
                ActionManagementGrpc.newBlockingStub(grpcServerRule.getChannel());
        InitializePageResponse response =
                blockingStub.initializePage(InitializePageRequest.getDefaultInstance());

        assertEquals(response.getPolls(0), EXAMPLE_POLL);

    }
}
