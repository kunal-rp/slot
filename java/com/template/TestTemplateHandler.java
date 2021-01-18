package com.template;

import com.template.MainTemplateHandler;
import com.template.TemplateServiceProto.GenerateScheduleRequest;

public class TestTemplateHandler {


    public static void main(String[] args) {

        MainTemplateHandler templateHandler = new MainTemplateHandler();

        System.out.println(templateHandler.generateSchedule(
        	GenerateScheduleRequest.newBuilder()
                    .setScheduleStartUnix(1610240520)
                    .setScheduleEndUnix(1610388800)
                    .build()));
    }
}

