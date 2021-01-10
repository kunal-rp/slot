package com.task;

import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.TimePolicy;
import com.task.TaskProto.TaskDetails;
import com.task.TaskProto.RecurringTime;
import com.task.TaskProto.DataCollectionPolicy;

/*  Create sample tasks for run testing */
public class SampleTasksUtil {

    
    public static TaskTemplate ONE_TIME_MEETING_TEMPLATE = 
        TaskTemplate.newBuilder()
            .setTemplateId(777777)
            .setDuration(6)
            .setDetails(TaskDetails.newBuilder()
                .setTitle("Prep Holy Pandas")
                .build())
            .setTimeConfiguration(
                TimePolicy.newBuilder()
                    .setStartTimestamp(20) 
                    .build())
            .build();

    public static TaskTemplate RECURRING_DAILY_MEETING_TEMPLATE = 
        TaskTemplate.newBuilder()
            .setTemplateId(8888888)
            .setDuration(2)
            .setDetails(TaskDetails.newBuilder()
                .setTitle("Virtual Check ")
                .setDescription(" Open EMail, check calendar for events of day")
                .build())
            .setTimeConfiguration(
                TimePolicy.newBuilder()
                    .setRecurringTime(
                        RecurringTime.newBuilder() 
                            .setStartTimestamp(7) 
                            .setIntervalSeconds(6) 
                        .build())
                    .build())
            .addDataCollectionConfiguration(
                DataCollectionPolicy.newBuilder()
                    .setKey("new_emails")
                    .setPrompt("# new emails")
                    .setCollectionType(DataCollectionPolicy.CollectionType.INT)
                    .build())
            .build();

}



