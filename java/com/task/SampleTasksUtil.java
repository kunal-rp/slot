package com.task;

import java.util.List;
import java.util.ArrayList;
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

    public static TaskTemplate PROD_DAILY_CALLING_TEMPLATE = 
        TaskTemplate.newBuilder()
            .setTemplateId(1001)
            .setDuration(900) // 15 min
            .setDetails(TaskDetails.newBuilder()
                .setTitle("Reconnect")
                .setDescription(" Call someone from family/friends")
                .build())
            .setTimeConfiguration(
                TimePolicy.newBuilder()
                    .setRecurringTime(
                        RecurringTime.newBuilder() 
                            .setStartTimestamp(1609520400) //1/1/21 17:00:00
                            .setIntervalSeconds(259200) // 3 day 
                        .build())
                    .build())
            .addDataCollectionConfiguration(
                DataCollectionPolicy.newBuilder()
                    .setKey("recipiant_name")
                    .setPrompt("Name")
                    .setCollectionType(DataCollectionPolicy.CollectionType.MARKDOWN)
                    .build())
            .addDataCollectionConfiguration(
                DataCollectionPolicy.newBuilder()
                    .setKey("notes")
                    .setPrompt("Notes to remember")
                    .setCollectionType(DataCollectionPolicy.CollectionType.MARKDOWN)
                    .build())
            .build();
    public static TaskTemplate PROD_DAILY_WORKOUT_TEMPLATE = 
        TaskTemplate.newBuilder()
            .setTemplateId(1001)
            .setDuration(4200) // 70 min
            .setDetails(TaskDetails.newBuilder()
                .setTitle("Workout")
                .setDescription("Daily exercise lets get it")
                .build())
            .setTimeConfiguration(
                TimePolicy.newBuilder()
                    .setRecurringTime(
                        RecurringTime.newBuilder() 
                            .setStartTimestamp(1609525800) //1/1/21 18:30:00
                            .setIntervalSeconds(86400) // 1 day 
                        .build())
                    .build())
            .addDataCollectionConfiguration(
                DataCollectionPolicy.newBuilder()
                    .setKey("workout_type")
                    .setPrompt("Workout Type")
                    .setCollectionType(DataCollectionPolicy.CollectionType.MARKDOWN)
                    .build())           
            .build();

    public static TaskTemplate PROD_MONTHLY_INVEST_TEMPLATE = 
        TaskTemplate.newBuilder()
            .setTemplateId(1002)
            .setDuration(1500) // 25 min
            .setDetails(TaskDetails.newBuilder()
                .setTitle("Index Investment")
                .setDescription(" Monthly investment in index funds")
                .build())
            .setTimeConfiguration(
                TimePolicy.newBuilder()
                    .setRecurringTime(
                        RecurringTime.newBuilder() 
                            .setStartTimestamp(1610308800) //1/10/21 20:00:00
                            .setIntervalSeconds(2592000) // 30 day 
                        .build())
                    .build())
            .addDataCollectionConfiguration(
                DataCollectionPolicy.newBuilder()
                    .setKey("new_investment_total")
                    .setPrompt("Total $ invested today")
                    .setCollectionType(DataCollectionPolicy.CollectionType.INT)
                    .build())
            .build();

    public static List<TaskTemplate> PROD_TEMPLATES = new ArrayList<TaskTemplate>(){{
        add(PROD_DAILY_CALLING_TEMPLATE);
        add(PROD_MONTHLY_INVEST_TEMPLATE);
        add(PROD_DAILY_WORKOUT_TEMPLATE);
    }};

}



