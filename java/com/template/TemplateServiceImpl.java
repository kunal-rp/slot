package com.template;

import io.grpc.stub.StreamObserver;
import com.template.TemplateServiceGrpc;
import com.template.TemplateServiceProto.GenerateScheduleRequest;
import com.template.TemplateServiceProto.GenerateScheduleResponse;
import com.template.GenerativeScheduleUtil;
import com.task.SampleTasksUtil;

public class TemplateServiceImpl extends TemplateServiceGrpc.TemplateServiceImplBase {

    @Override
    public void generateScheduleFromTemplates(GenerateScheduleRequest req,
            StreamObserver<GenerateScheduleResponse> responseObserver) {

    	GenerativeScheduleUtil scheduleUtil = new GenerativeScheduleUtil();

        responseObserver.onNext(
        	GenerateScheduleResponse.newBuilder()
        		.addAllGeneratedTaskEntries(
        			scheduleUtil.generateSchedule(
        				SampleTasksUtil.PROD_TEMPLATES,
        				(int) req.getScheduleStartUnix(),
        				(int) req.getScheduleEndUnix()))
        		.build());
        responseObserver.onCompleted();
    }

}
