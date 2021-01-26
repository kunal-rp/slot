package com.task;

import java.util.List;
import com.task.TaskServiceProto.GenerateScheduleRequest;
import com.task.TaskServiceProto.GenerateScheduleResponse;

/* Fake implementation of Task Service Handler - mainly for tests  */ 
public class FakeTaskHandler implements TaskHandler {

    public GenerateScheduleResponse generateSchedule(GenerateScheduleRequest request){
    	 
        return GenerateScheduleResponse.getDefaultInstance();

    }

}
