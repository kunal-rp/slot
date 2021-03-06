package com.task;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.TaskEntry;
import com.google.common.collect.Iterables;
import com.task.template.GenerativeScheduleUtil;
import com.task.SampleTasksUtil;

/**
 * Generative Schedule Util Test
 */
public class GenerativeScheduleUtilTest {

    GenerativeScheduleUtil scheduleUtil = new GenerativeScheduleUtil();

    @Test
    public void shouldGenerateOneRecurringEntry() throws Exception {
    	
        ArrayList<TaskTemplate> templates = new ArrayList() {{
            add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, interval: 6
        }};

        TaskEntry entry = Iterables.getOnlyElement(scheduleUtil.generateSchedule(templates, 6, 9));

        assertEquals(entry.getStartTimestamp(), 7);
        // hardcoded generated task id
        assertEquals(entry.getGeneratedEntryId(), 1001);

    }

    @Test
    public void shouldGenerateMultipleRecurringEntry() throws Exception {
    	
        ArrayList<TaskTemplate> templates = new ArrayList() {{
            add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, interval: 6
        }};

        List<TaskEntry> entries = scheduleUtil.generateSchedule(templates, 6, 20);

        assertEquals(entries.get(0).getStartTimestamp(), 7);
        assertEquals(entries.get(0).getOccurance(), 1);
        assertEquals(entries.get(1).getStartTimestamp(), 13);
        assertEquals(entries.get(1).getOccurance(), 2);
        assertEquals(entries.get(2).getStartTimestamp(), 19);
        assertEquals(entries.get(2).getOccurance(), 3);

    }

     @Test
    public void shouldGenerateReccuringEntry_thatStartsBeforeAndEndsDuringSlot() throws Exception {
        
        ArrayList<TaskTemplate> templates = new ArrayList() {{
            add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, interval: 6, duration: 2
        }};

        TaskEntry entry = Iterables.getOnlyElement(scheduleUtil.generateSchedule(templates,8, 9));

        // there is 
        assertEquals(entry.getStartTimestamp(), 7);
        // hardcoded generated task id
        assertEquals(entry.getGeneratedEntryId(), 1001);
    }



    @Test
    public void shouldGenerateNoEntries() throws Exception {
    	
        ArrayList<TaskTemplate> templates = new ArrayList() {{
            add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, interval: 6,duration: 2
        }};

         List<TaskEntry> entries = scheduleUtil.generateSchedule(templates, 10, 11);

        assertEquals(entries.size(), 0);

    }
}
