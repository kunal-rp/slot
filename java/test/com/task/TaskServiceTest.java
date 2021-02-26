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
import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.TaskEntry;
import com.task.TaskProto.TimeAlteractionPolicy;
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
    public void shouldReturnOneRecurringGeneratedEntry() throws Exception {
        fakeTaskDBHandler.setTemplateDb(
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
        assertEquals(response.getGeneratedTaskEntry(0).getGeneratedEntryId(), 1001);
    }

    @Test
    public void shouldReturnOneEntry_onlyStored() throws Exception {
        TaskTemplate template = SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE;
        fakeTaskDBHandler.setTemplateDb(
            new ArrayList() {{
                add(template); // start time : 7, duration: 2, interval: 6
        }});

        fakeTaskDBHandler.setEntryDb(new ArrayList() {{
                add(
                    TaskEntry.newBuilder()
                        .setEntryId(12345)
                        .setTemplateId(template.getTemplateId())
                        .setOccurance(1)
                        .setStartTimestamp(template.getTimeConfiguration().getStartTimestamp())
                        .build());
        }});

        TaskServiceGrpc.TaskServiceBlockingStub blockingStub = createBlockingStub();
        GenerateScheduleResponse response =
                blockingStub.generateSchedule(
                    GenerateScheduleRequest.newBuilder()
                        .setScheduleStartUnix(6)
                        .setScheduleEndUnix(9)
                    .build());

        assertEquals(response.getGeneratedTaskEntryCount(), 1);
        assertEquals(response.getGeneratedTaskEntry(0).getEntryId(), 12345);
    }

    @Test
    public void shouldReturnTwoEntry_onlyStored() throws Exception {
        TaskTemplate template = SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE;
        fakeTaskDBHandler.setTemplateDb(
            new ArrayList() {{
                add(template); // start time : 7, duration: 2, interval: 6
        }});

        fakeTaskDBHandler.setEntryDb(new ArrayList() {{
                add(
                    TaskEntry.newBuilder()
                        .setEntryId(12345)
                        .setTemplateId(template.getTemplateId())
                        .setOccurance(1)
                        .setStartTimestamp(template.getTimeConfiguration().getStartTimestamp())
                        .build());
                 add(
                    TaskEntry.newBuilder()
                        .setEntryId(67890)
                        .setTemplateId(template.getTemplateId())
                        .setOccurance(2)
                        .setStartTimestamp(template.getTimeConfiguration().getStartTimestamp() + template.getTimeConfiguration().getIntervalSeconds())
                        .build());
        }});

        TaskServiceGrpc.TaskServiceBlockingStub blockingStub = createBlockingStub();
        GenerateScheduleResponse response =
                blockingStub.generateSchedule(
                    GenerateScheduleRequest.newBuilder()
                        .setScheduleStartUnix(6)
                        .setScheduleEndUnix(14)
                    .build());

        assertEquals(response.getGeneratedTaskEntryCount(), 2);
        assertEquals(response.getGeneratedTaskEntry(0).getEntryId(), 12345);
        assertEquals(response.getGeneratedTaskEntry(1).getEntryId(), 67890);
    }

    @Test
    public void shouldNotReturnAnyEntries_occuranceEntryWithUpdatedTime() throws Exception {
        TaskTemplate template = SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE;
        fakeTaskDBHandler.setTemplateDb(
            new ArrayList() {{
                add(template); // start time : 7, duration: 2, interval: 6
        }});

        fakeTaskDBHandler.setEntryDb(new ArrayList() {{
                add(
                    TaskEntry.newBuilder()
                        .setTemplateId(template.getTemplateId())
                        .setOccurance(1)
                        .setStartTimestamp(template.getTimeConfiguration().getStartTimestamp())
                        .setTimeAlterations(
                            TimeAlteractionPolicy.newBuilder()
                                .setStartTimestampSurplus(10))
                        .build());
        }});

        TaskServiceGrpc.TaskServiceBlockingStub blockingStub = createBlockingStub();
        GenerateScheduleResponse response =
                blockingStub.generateSchedule(
                    GenerateScheduleRequest.newBuilder()
                        .setScheduleStartUnix(6)
                        .setScheduleEndUnix(9)
                    .build());

        assertEquals(response.getGeneratedTaskEntryCount(), 0);
    }

    @Test
    public void shouldReturnOneEntryandOneStoredEntry_relatedEntries() throws Exception {
        TaskTemplate template = SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE;
        fakeTaskDBHandler.setTemplateDb(
            new ArrayList() {{
                add(template); // start time : 7, duration: 2, interval: 6
        }});

        fakeTaskDBHandler.setEntryDb(new ArrayList() {{
                 add(
                    TaskEntry.newBuilder()
                        .setEntryId(67890)
                        .setTemplateId(template.getTemplateId())
                        .setOccurance(2)
                        .setStartTimestamp(template.getTimeConfiguration().getStartTimestamp() + template.getTimeConfiguration().getIntervalSeconds())
                        .build());
        }});

        TaskServiceGrpc.TaskServiceBlockingStub blockingStub = createBlockingStub();
        GenerateScheduleResponse response =
                blockingStub.generateSchedule(
                    GenerateScheduleRequest.newBuilder()
                        .setScheduleStartUnix(6)
                        .setScheduleEndUnix(14)
                    .build());

        assertEquals(response.getGeneratedTaskEntryCount(), 2);
        assertEquals(response.getGeneratedTaskEntry(1).getGeneratedEntryId(),1001 );
        assertEquals(response.getGeneratedTaskEntry(0).getEntryId(), 67890);
    }

    @Test
    public void shouldReturnOneEntryandOneStoredEntry_unrelatedEntries() throws Exception {
        fakeTaskDBHandler.setTemplateDb(
            new ArrayList() {{
                add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, duration: 2, interval: 6
        }});

        fakeTaskDBHandler.setEntryDb(new ArrayList() {{
                add(SampleTasksUtil.ONE_TIME_INDIVIDUAL_IP_ENTRY); // start time : 8, duration: 3
        }});

        TaskServiceGrpc.TaskServiceBlockingStub blockingStub = createBlockingStub();
        GenerateScheduleResponse response =
                blockingStub.generateSchedule(
                    GenerateScheduleRequest.newBuilder()
                        .setScheduleStartUnix(6)
                        .setScheduleEndUnix(9)
                    .build());

        assertEquals(response.getGeneratedTaskEntryCount(), 2);
        assertEquals(response.getGeneratedTaskEntry(1).getEntryId(), SampleTasksUtil.ONE_TIME_INDIVIDUAL_IP_ENTRY.getEntryId());
    }


    //Test Mainly for fake handler logic 
    @Test
    public void shouldFetchNoTemplatesOrEntries() throws Exception {

        TaskServiceGrpc.TaskServiceBlockingStub blockingStub = createBlockingStub();
        GenerateScheduleResponse response =
                blockingStub.generateSchedule(
                    GenerateScheduleRequest.newBuilder()
                        .setScheduleStartUnix(6)
                        .setScheduleEndUnix(9)
                    .build());

        assertEquals(response.getGeneratedTaskEntryCount(), 0);
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