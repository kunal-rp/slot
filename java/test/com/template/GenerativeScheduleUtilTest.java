package com.template;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.TaskEntry;
import com.google.common.collect.Iterables;
import com.template.GenerativeScheduleUtil;
import com.task.SampleTasksUtil;

/**
 * Generative Schedule Util Test
 */
public class GenerativeScheduleUtilTest {

    GenerativeScheduleUtil scheduleUtil = new GenerativeScheduleUtil();

    @Test
    public void shouldGenerateOneOneTimeEntry() throws Exception {

        ArrayList<TaskTemplate> templates = new ArrayList() {{
            add(SampleTasksUtil.ONE_TIME_MEETING_TEMPLATE); // start time : 20
        }};

        TaskEntry entry = Iterables.getOnlyElement(scheduleUtil.generateSchedule(templates, 10, 21));

        assertEquals(entry.getStartTimestamp(), 20);

    }

    @Test
    public void shouldGenerateOneRecurringEntry() throws Exception {
    	
        ArrayList<TaskTemplate> templates = new ArrayList() {{
            add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, interval: 6
        }};

        TaskEntry entry = Iterables.getOnlyElement(scheduleUtil.generateSchedule(templates, 6, 9));

        assertEquals(entry.getStartTimestamp(), 7);

    }

    @Test
    public void shouldGenerateMultipleRecurringEntry() throws Exception {
    	
        ArrayList<TaskTemplate> templates = new ArrayList() {{
            add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, interval: 6
        }};

        List<TaskEntry> entries = scheduleUtil.generateSchedule(templates, 6, 20);

        assertEquals(entries.size(), 3);
        assertEquals(entries.get(0).getStartTimestamp(), 7);
        assertEquals(entries.get(0).getOccurance(), 1);
        assertEquals(entries.get(1).getStartTimestamp(), 13);
        assertEquals(entries.get(1).getOccurance(), 2);
        assertEquals(entries.get(2).getStartTimestamp(), 19);
        assertEquals(entries.get(2).getOccurance(), 3);

    }

    @Test
    public void shouldGenerateMultipleRecurringAndOneTimeEntries() throws Exception {
    	
        ArrayList<TaskTemplate> templates = new ArrayList() {{
        	add(SampleTasksUtil.ONE_TIME_MEETING_TEMPLATE); // start time : 20
            add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, interval: 6
        }};

        List<TaskEntry> entries = scheduleUtil.generateSchedule(templates, 6, 20);

        assertEquals(entries.size(), 4);
        assertEquals(entries.get(0).getStartTimestamp(), 20);
        assertEquals(entries.get(1).getStartTimestamp(), 7);
        assertEquals(entries.get(1).getOccurance(), 1);
        assertEquals(entries.get(2).getStartTimestamp(), 13);
        assertEquals(entries.get(2).getOccurance(), 2);
        assertEquals(entries.get(3).getStartTimestamp(), 19);
        assertEquals(entries.get(3).getOccurance(), 3);

    }

     @Test
    public void shouldGenerateReccuringEntry_thatStartsBeforeAndEndsDuringSlot() throws Exception {
        
        ArrayList<TaskTemplate> templates = new ArrayList() {{
            add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, interval: 6, duration: 2
        }};

        TaskEntry entry = Iterables.getOnlyElement(scheduleUtil.generateSchedule(templates,8, 9));

        // there is 
        assertEquals(entry.getStartTimestamp(), 7);

    }



    @Test
    public void shouldGenerateNoEntries() throws Exception {
    	
        ArrayList<TaskTemplate> templates = new ArrayList() {{
            add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE); // start time : 7, interval: 6
        }};

         List<TaskEntry> entries = scheduleUtil.generateSchedule(templates, 0, 3);

        assertEquals(entries.size(), 0);

    }
}
