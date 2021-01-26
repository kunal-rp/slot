package com.task.db;

import java.util.Random;
import java.util.List;
import java.util.concurrent.Future;
import com.google.common.util.concurrent.Futures;
import com.task.TaskProto.TaskTemplate;
import com.task.SampleTasksUtil;

public class MainTaskDBHandler implements TaskDBHandler{

    public List<TaskTemplate> fetchTemplatesForTimeslot(int startUnix, int endUnix){
    	return SampleTasksUtil.PROD_TEMPLATES;
    }

    public Future<TaskTemplate> insertNewTemplate(TaskTemplate templateDetailsToInsert){
    	return Futures.immediateFuture(templateDetailsToInsert.toBuilder().setTemplateId((new Random()).nextInt(100)).build());
    }

    public void deleteTemplate(int templateId){

    }

}
