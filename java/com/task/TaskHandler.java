package com.task;

import com.task.TaskServiceProto.GenerateScheduleRequest;
import com.task.TaskServiceProto.GenerateScheduleResponse;

/* 
	Inter Service Handler for Task Service 
	Abstracts creation of client while also providing interface for easy faking within tests
*/ 
public interface TaskHandler {

    public GenerateScheduleResponse generateSchedule(GenerateScheduleRequest request);

}
