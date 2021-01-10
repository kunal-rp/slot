package com.eval;

import com.task.TaskProto.TaskTemplate;
import java.util.List;
import java.util.ArrayList;
import com.task.SampleTasksUtil;

/*  Eval Micro Service */
public class TestEvalServer {

    private static clList<TaskTemplate> TEMPLATES = new ArrayList<TaskTemplate>() {{
        add(SampleTasksUtil.ONE_TIME_MEETING_TEMPLATE);
        add(SampleTasksUtil.RECURRING_DAILY_MEETING_TEMPLATE);
    }};

    public static void main (String[] args){

        GenerativeScheduleUtil scheduleUtil = new GenerativeScheduleUtil();

        long startTime = System.nanoTime();
        System.out.println(scheduleUtil.generateSchedule(TEMPLATES, 10,12));
        System.out.println(System.nanoTime() - startTime);  

    }

}

