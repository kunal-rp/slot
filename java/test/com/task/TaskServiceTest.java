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
import com.google.common.collect.Iterables;
import com.task.TaskServiceGrpc;
import com.task.TaskServiceProto.GenerateScheduleRequest;
import com.task.TaskServiceProto.GenerateScheduleResponse;
import com.task.TaskServiceProto.CreateNewTemplatesRequest;
import com.task.TaskServiceProto.CreateNewTemplatesResponse;
import com.task.TaskServiceProto.UpdateTemplateRequest;
import com.task.TaskServiceProto.UpdateTemplateResponse;
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

        TaskServiceGrpc.TaskServiceBlockingStub blockingStub = createBlockingStub();
        GenerateScheduleResponse response =
                blockingStub.generateSchedule(
                    GenerateScheduleRequest.newBuilder()
                        .setScheduleStartUnix(6)
                        .setScheduleEndUnix(9)
                    .build());

        assertEquals(response.getGeneratedTaskEntryCount(), 1);
    }

     @Test
    public void shouldInsertNewTemplate() throws Exception {

        TaskServiceGrpc.TaskServiceBlockingStub blockingStub = createBlockingStub();
        CreateNewTemplatesResponse response =
                blockingStub.createNewTemplates(
                    CreateNewTemplatesRequest.newBuilder()
                        .addTemplatesToAdd(
                            SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE.toBuilder().clearTemplateId().build())
                    .build());

        assertEquals(Iterables.getOnlyElement(response.getCreatedTemplatesList()).getDetails(), SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE.getDetails());
        assertEquals(response.getCreatedTemplatesList(), fakeTaskDBHandler.getTemplatesAdded());
    }

    @Test
    public void shouldAlterTemplate() throws Exception {

        fakeTaskDBHandler.setTemplateDb(
            new ArrayList() {{
                add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, interval: 6
        }});

        TaskServiceGrpc.TaskServiceBlockingStub blockingStub = createBlockingStub();
        UpdateTemplateResponse response =
                blockingStub.updateTemplate(
                    UpdateTemplateRequest.newBuilder()
                        .setTemplateId(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE.getTemplateId())
                        .setEndTimestamp(101)
                    .build());
        //only alter end timestamp
        assertEquals(Iterables.getOnlyElement(fakeTaskDBHandler.getTemplateDb()).getTimeConfiguration().getEndTimestamp(),101);
        assertEquals(Iterables.getOnlyElement(fakeTaskDBHandler.getTemplateDb()).getDetails(),SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE.getDetails());
    }

    private TaskServiceGrpc.TaskServiceBlockingStub createBlockingStub() throws Exception{
        // Add the service to the in-process server.
         grpcCleanup.register(
            serverBuilder.addService(injector.getInstance(TaskServiceImpl.class)).build().start());
        ManagedChannel channel = grpcCleanup.register(
            channelBuilder.maxInboundMessageSize(1024).build());

        return TaskServiceGrpc.newBlockingStub(channel);

    }


}