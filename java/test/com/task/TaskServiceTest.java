package com.task;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import io.grpc.ManagedChannel;
import io.grpc.testing.GrpcCleanupRule;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.inprocess.InProcessChannelBuilder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Guice;
import com.task.TaskServiceGrpc;
import com.task.TaskServiceProto.GenerateScheduleRequest;
import com.task.TaskServiceProto.GenerateScheduleResponse;
import com.task.SampleTasksUtil;
import com.task.TaskServiceImpl;
import com.task.db.FakeTaskDBHandler;
import com.util.FakeServiceModule;

@RunWith(JUnit4.class)
public class TaskServiceTest {

    protected Injector injector = Guice.createInjector(new FakeServiceModule());

    @Inject
    private FakeTaskDBHandler fakeTaskDBHandler;

    @Rule
    public GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();
    
    private String serverName = InProcessServerBuilder.generateName();
    private InProcessServerBuilder serverBuilder = InProcessServerBuilder
          .forName(serverName).directExecutor();
    private InProcessChannelBuilder channelBuilder = InProcessChannelBuilder
          .forName(serverName).directExecutor();

    @Before
    public void setup() {
        injector.injectMembers(this);
    }

    @After
    public void cleanUp() {
        fakeTaskDBHandler.reset();
    }


    @Test
    public void shouldGenerateOneRecurringEntry() throws Exception {
        fakeTaskDBHandler.setTemplatesToReturnInTimeslot(
            new ArrayList() {{
                add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, interval: 6
        }});

        // Add the service to the in-process server.
         grpcCleanup.register(
            serverBuilder.addService(injector.getInstance(TaskServiceImpl.class)).build().start());
        ManagedChannel channel = grpcCleanup.register(
            channelBuilder.maxInboundMessageSize(1024).build());

        TaskServiceGrpc.TaskServiceBlockingStub blockingStub = TaskServiceGrpc.newBlockingStub(channel);
        GenerateScheduleResponse response =
                blockingStub.generateSchedule(
                    GenerateScheduleRequest.newBuilder()
                        .setScheduleStartUnix(6)
                        .setScheduleEndUnix(9)
                    .build());

        assertEquals(response.getGeneratedTaskEntryCount(), 0);

    }
}