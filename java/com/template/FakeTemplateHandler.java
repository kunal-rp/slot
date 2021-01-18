package com.template;

import java.util.List;
import com.template.TemplateServiceProto.GenerateScheduleRequest;
import com.template.TemplateServiceProto.GenerateScheduleResponse;

/* Fake implementation of Template Service Handler - mainly for tests  */ 
public class FakeTemplateHandler {

    public GenerateScheduleResponse generateSchedule(GenerateScheduleRequest request){
    	 
        return GenerateScheduleResponse.getDefaultInstance();

    }

}
