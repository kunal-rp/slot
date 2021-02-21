package com.task.template;

import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.TaskEntry;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.lang.Math;

/*  Eval Micro Service */
public class GenerativeScheduleUtil {

    /** 
    * Generates Task Entries whose start timestamps fall in a given time schedule 
    * TODO : Modify s.t. entries that end inside slot are included
    * TODO : update to filter templates pre call 
    * @param templates all task templates that fall into timeslot
    * @param scheduleStartTimestamp starting timestamp of schedule slot
    * @param scheduleEndTimestamp starting timestamp of schedule slot
    * @return List<TaskEntry> 
    */  
    public List<TaskEntry> generateSchedule(List<TaskTemplate> templates, int scheduleStartTimestamp, int scheduleEndTimestamp ){
        ArrayList<TaskEntry> resultingEntries = new ArrayList<TaskEntry>();

        for(TaskTemplate template : templates){
            resultingEntries.addAll(generateRecurringTimeEntries(template,calculateFirstOccurance(scheduleStartTimestamp, template),calculateLastOccurance(scheduleEndTimestamp, template)));
        }

        return resultingEntries;
    }

    private int calculateFirstOccurance(int scheduleStartTimestamp, TaskTemplate template){
        int firstOcc =  (int) Math.ceil(
            (scheduleStartTimestamp - template.getTimeConfiguration().getStartTimestamp()) /
                (double) template.getTimeConfiguration().getIntervalSeconds());

        // iff the calcualted first occurance(CFO) is not the beginning one for template,
        // & the end timne of the CFO -1 occurs after the start time of the slot , 
        // return CFO - 1
        return (firstOcc > 0 && template.getTimeConfiguration().getStartTimestamp() + ((firstOcc - 1) * template.getTimeConfiguration().getIntervalSeconds()) + template.getDuration() > scheduleStartTimestamp ? firstOcc - 1 : firstOcc  );

    }

     private int calculateLastOccurance(int scheduleEndTimestamp, TaskTemplate template){
        return (int) Math.floor((scheduleEndTimestamp - template.getTimeConfiguration().getStartTimestamp()) / (double) template.getTimeConfiguration().getIntervalSeconds());
    }


    private TaskEntry generateTimeEntry(TaskTemplate taskTemplate) {
        return TaskEntry.newBuilder()
            .setEntryId(1001)
            .setTemplateId(taskTemplate.getTemplateId())
            .setStartTimestamp(taskTemplate.getTimeConfiguration().getStartTimestamp())
            .setDuration(taskTemplate.getDuration())
            .build();

    }   

    private List<TaskEntry> generateRecurringTimeEntries(TaskTemplate taskTemplate, int firstOccurance, int lastOccurance){
        ArrayList<TaskEntry> resultingEntries = new ArrayList<TaskEntry>();

        for(int occurance = firstOccurance ; occurance <= lastOccurance ; occurance++ ){
            TaskEntry entry = generateTimeEntry(taskTemplate);

            // will need to -1 to occurance b/c when calculating the first/last occurance, the first ever event is labeled as 1 & it occurs before the first interval
            long startTime = taskTemplate.getTimeConfiguration().getStartTimestamp() + taskTemplate.getTimeConfiguration().getIntervalSeconds() * (occurance);
            //update entry start time based on occurance number
            resultingEntries.add(entry.toBuilder().setStartTimestamp(startTime).setOccurance(occurance + 1).build());
        }
        return resultingEntries;
    }
}

