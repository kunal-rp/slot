package com.eval;

import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.TaskEntry;
import com.task.TaskProto.TimePolicy;
import com.task.TaskProto.RecurringTime;
import com.task.SampleTasksUtil;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.lang.Math;

/*  Eval Micro Service */
public class ScheduleHandler {

    List<TaskTemplate> TEMPLATES = new ArrayList<TaskTemplate>() {{
        add(SampleTasksUtil.ONE_TIME_MEETING_TEMPLATE);
        add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE);
    }};

    /** 
    * Generates Task Entries whose start timestamps fall in a given time schedule 
    * TODO : Modify s.t. entries that end inside slot are included
    * @param scheduleStartTimestamp starting timestamp of schedule slot
    * @param scheduleEndTimestamp starting timestamp of schedule slot
    * @return List<TaskEntry> 
    */  
    public  List<TaskEntry> generateSchedule(int scheduleStartTimestamp, int scheduleEndTimestamp ){

        /*
            1) get list of all task templates whose: 
                - (one time) start_timestamp is within schedule slot or (recurring) end time is not before slot start time
            2) generate one time templates
                - no changes, just return 
            3) generate recurring templates occurances
                - fist occurance # = ((slot st - task st) / inv) + (slot st R inv == 0 ? 0 : 1)
                - last occurance # = roundDown((slot et - task st )/ inv)
        */

        ArrayList<TaskEntry> resultingEntries = new ArrayList<TaskEntry>();

        //following block would be handled by db query search
        List<TaskTemplate> slottedTaskTemplates = 
            TEMPLATES.stream().filter(
                template -> (template.getTimeConfiguration().getStartTimestamp() != 0 ?
                 scheduleStartTimestamp <= template.getTimeConfiguration().getStartTimestamp() && scheduleEndTimestamp >= template.getTimeConfiguration().getStartTimestamp() :  // one time 
                 (template.getTimeConfiguration().getRecurringTime().getEndTimestamp() == 0 ? true : scheduleStartTimestamp <= template.getTimeConfiguration().getRecurringTime().getEndTimestamp() ))) //reoccuring
            .collect(Collectors.toList());


        for(TaskTemplate template : slottedTaskTemplates){
            
            if(template.getTimeConfiguration().getStartTimestamp() == 0){
                resultingEntries.addAll(generateRecurringTimeEntries(template,calculateFirstOccurance(scheduleStartTimestamp, template),calculateLastOccurance(scheduleEndTimestamp, template)));
            }else {
                resultingEntries.add(generateTimeEntry(template) );
            } 
        }

        return resultingEntries;

    }

    private int calculateFirstOccurance(int scheduleStartTimestamp, TaskTemplate template){
        return (int) Math.ceil((scheduleStartTimestamp - template.getTimeConfiguration().getRecurringTime().getStartTimestamp()) / template.getTimeConfiguration().getRecurringTime().getIntervalSeconds()) + ((scheduleStartTimestamp - template.getTimeConfiguration().getRecurringTime().getStartTimestamp()) % template.getTimeConfiguration().getRecurringTime().getIntervalSeconds() == 0 ? 0 : 1);
    }

     private int calculateLastOccurance(int scheduleEndTimestamp, TaskTemplate template){
        return (int) Math.ceil((scheduleEndTimestamp - template.getTimeConfiguration().getRecurringTime().getStartTimestamp()) / template.getTimeConfiguration().getRecurringTime().getIntervalSeconds());
    }


    private TaskEntry generateTimeEntry(TaskTemplate taskTemplate) {
        return TaskEntry.newBuilder()
            .setEntryId(1001)
            .setTemplateId(taskTemplate.getTemplateId())
            .setDetails(taskTemplate.getDetails())
            .setStartTimestamp(taskTemplate.getTimeConfiguration().getStartTimestamp())
            .setDuration(taskTemplate.getDuration())
            .addAllDataCollectionConfiguration(taskTemplate.getDataCollectionConfigurationList())
            .build();

    }   

    private List<TaskEntry> generateRecurringTimeEntries(TaskTemplate taskTemplate, int firstOccurance, int lastOccurance){
        ArrayList<TaskEntry> resultingEntries = new ArrayList<TaskEntry>();

        for(int occurance = firstOccurance ; occurance <= lastOccurance ; occurance++ ){
            TaskEntry entry = generateTimeEntry(taskTemplate);

            long startTime = taskTemplate.getTimeConfiguration().getRecurringTime().getStartTimestamp() + taskTemplate.getTimeConfiguration().getRecurringTime().getIntervalSeconds() * occurance;
            //update entry start time based on occurance number
            resultingEntries.add(entry.toBuilder().setStartTimestamp(startTime).setOccurance(occurance).build());
        }
        return resultingEntries;
    }
}

