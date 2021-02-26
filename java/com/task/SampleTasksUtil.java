 package com.task;

import java.util.List;
import java.util.ArrayList;
import com.task.TaskProto.TaskEntry;
import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.RecurringTimePolicy;
import com.task.TaskProto.TaskDetails;
import com.task.TaskProto.DataCollection;
import com.task.TaskProto.DataCollectionPolicy;

/*  Create sample tasks for run testing */
public class SampleTasksUtil {

    public static  DataCollectionPolicy NEW_EMAIL_DCP =
         DataCollectionPolicy.newBuilder()
            .setKey("new_emails")
            .setPrompt("# new emails")
            .setCollectionType(DataCollectionPolicy.CollectionType.INT)
            .build();

      public static DataCollection NEW_EMAIL_DC = 
        DataCollection.newBuilder()
            .setDataCollectionId(222)
            .addPolicy(NEW_EMAIL_DCP)
            .build();

    public static  DataCollectionPolicy DAILY_CALL_RECIPIENT_DCP =
          DataCollectionPolicy.newBuilder()
            .setKey("recipiant_name")
            .setPrompt("Name")
            .setCollectionType(DataCollectionPolicy.CollectionType.MARKDOWN)
            .build();

     public static  DataCollectionPolicy GENERAL_NOTES_DCP =
          DataCollectionPolicy.newBuilder()
            .setKey("notes")
            .setPrompt("Notes to remember")
            .setCollectionType(DataCollectionPolicy.CollectionType.MARKDOWN)
            .build();

    public static DataCollection DAILY_CALL_DC = 
        DataCollection.newBuilder()
            .setDataCollectionId(233)
            .addPolicy(DAILY_CALL_RECIPIENT_DCP)
            .addPolicy(GENERAL_NOTES_DCP)
            .build();

     public static  DataCollectionPolicy WORKOUT_DCP =
        DataCollectionPolicy.newBuilder()
            .setKey("workout_type")
            .setPrompt("Workout Type")
            .setCollectionType(DataCollectionPolicy.CollectionType.MARKDOWN)
            .build();

      public static DataCollection WORKOUT_DC = 
        DataCollection.newBuilder()
            .setDataCollectionId(244)
            .addPolicy(WORKOUT_DCP)
            .build();

     public static  DataCollectionPolicy INVESTMENT_DCP =
        DataCollectionPolicy.newBuilder()
            .setKey("new_investment_total")
            .setPrompt("Total $ invested today")
            .setCollectionType(DataCollectionPolicy.CollectionType.INT)
            .build();

      public static DataCollection INVESTMENT_DC = 
        DataCollection.newBuilder()
            .setDataCollectionId(255)
            .addPolicy(INVESTMENT_DCP)
            .addPolicy(GENERAL_NOTES_DCP)
            .build();



    public static TaskTemplate RECURRING_DAILY_MEETING_TEMPLATE = 
        TaskTemplate.newBuilder()
            .setTemplateId(8888000)
            .setDuration(2)
            .setDetails(TaskDetails.newBuilder()
                .setTitle("Virtual Check ")
                .setDescription(" Open EMail, check calendar for events of day")
                .build())
            .setTimeConfiguration(
                RecurringTimePolicy.newBuilder()
                    .setStartTimestamp(7) 
                    .setIntervalSeconds(6) 
                .build())
            .addDataCollectionId(NEW_EMAIL_DC.getDataCollectionId())
            .build();

    public static TaskTemplate PROD_DAILY_CALLING_TEMPLATE = 
        TaskTemplate.newBuilder()
            .setTemplateId(991000)
            .setDuration(900) // 15 min
            .setDetails(TaskDetails.newBuilder()
                .setTitle("Reconnect")
                .setDescription(" Call someone from family/friends")
                .build())
            .setTimeConfiguration(
                RecurringTimePolicy.newBuilder()
                    .setStartTimestamp(1609520400) //1/1/21 17:00:00
                    .setIntervalSeconds(259200) // 3 day 
                .build())
            .addDataCollectionId(DAILY_CALL_DC.getDataCollectionId())
            .build();
    public static TaskTemplate PROD_DAILY_WORKOUT_TEMPLATE = 
        TaskTemplate.newBuilder()
            .setTemplateId(99001)
            .setDuration(4200) // 70 min
            .setDetails(TaskDetails.newBuilder()
                .setTitle("Workout")
                .setDescription("Daily exercise lets get it")
                .build())
            .setTimeConfiguration(
                RecurringTimePolicy.newBuilder()
                    .setStartTimestamp(1609525800) //1/1/21 18:30:00
                    .setIntervalSeconds(86400) // 1 day 
                .build())
            .addDataCollectionId(WORKOUT_DC.getDataCollectionId())           
            .build();

    public static TaskTemplate PROD_MONTHLY_INVEST_TEMPLATE = 
        TaskTemplate.newBuilder()
            .setTemplateId(99002)
            .setDuration(1500) // 25 min
            .setDetails(TaskDetails.newBuilder()
                .setTitle("Index Investment")
                .setDescription(" Monthly investment in index funds")
                .build())
            .setTimeConfiguration(
                RecurringTimePolicy.newBuilder()
                    .setStartTimestamp(1610308800) //1/10/21 20:00:00
                    .setIntervalSeconds(2592000) // 30 day 
                .build())
            .addDataCollectionId(INVESTMENT_DC.getDataCollectionId())
            .build();

     public static TaskEntry ONE_TIME_INDIVIDUAL_IP_ENTRY = 
        TaskEntry.newBuilder()
            .setEntryId(55000)
            // no template id 
            .setStartTimestamp(8)
            .setDuration(3) 
            .setStatus(TaskEntry.TaskStatus.ACTIVE)
            .build();

    public static List<TaskTemplate> PROD_TEMPLATES = new ArrayList<TaskTemplate>(){{
        add(PROD_DAILY_CALLING_TEMPLATE);
        add(PROD_MONTHLY_INVEST_TEMPLATE);
        add(PROD_DAILY_WORKOUT_TEMPLATE);
    }};

    public static List<TaskEntry> PROD_ENTRYS = new ArrayList<TaskEntry>(){{
    
    }};

}



