package com.template;

import com.template.TemplateServiceProto.GenerateScheduleRequest;
import com.template.TemplateServiceProto.GenerateScheduleResponse;

/* 
	Inter Service Handler for Template Service 
	Abstracts creation of client while also providing interface for easy faking within tests
*/ 
public interface TemplateHandler {

    public GenerateScheduleResponse generateSchedule(GenerateScheduleRequest request);

}
