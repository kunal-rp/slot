package com.task.db;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;
import com.google.common.util.concurrent.Futures;
import com.task.TaskProto.TaskTemplate;

public class FakeTaskDBHandler implements TaskDBHandler{

	private List<TaskTemplate> templatesInTimeSlot = new ArrayList<TaskTemplate>();
	private List<TaskTemplate> templatesAdded = new ArrayList<TaskTemplate>();
	private boolean failOperation = false;


    public List<TaskTemplate> fetchTemplatesForTimeslot(int startUnix, int endUnix){

    	if(!failOperation){
    		return templatesInTimeSlot;
    	}else{
    		throw new java.lang.UnsupportedOperationException("Fake DB Insert error");
    	}
    }

    public Future<TaskTemplate> insertNewTemplate(TaskTemplate templateDetailsToInsert){
    	if(!failOperation){
    		TaskTemplate updatedTemplate = 
    			templateDetailsToInsert.toBuilder()
    			.setTemplateId(
    				(new Random()).nextInt(100)
    			).build();
    		templatesAdded.add(updatedTemplate);
    		return Futures.immediateFuture(updatedTemplate);
    	}else{
    		throw new java.lang.UnsupportedOperationException("Fake DB Insert error");
    	}
    }

    public void deleteTemplate(int templateId){

    }

    public void reset() {
    	templatesInTimeSlot = new ArrayList<TaskTemplate>();
    	failOperation = false;
    }

    public void setTemplatesToReturnInTimeslot(List<TaskTemplate> templatesInTimeSlot){
    	this.templatesInTimeSlot = templatesInTimeSlot;
    }

    public void setFailOperation(boolean failOperation){
    	this.failOperation = failOperation;
    }

}
