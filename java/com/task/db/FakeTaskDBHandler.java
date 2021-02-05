package com.task.db;

import static java.util.stream.Collectors.toList;

import java.util.Random;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import com.google.common.util.concurrent.Futures;
import com.task.TaskProto.TimeAlteractionPolicy;
import com.google.common.util.concurrent.ListenableFuture;
import com.task.TaskProto.TaskTemplate;
import com.google.inject.Singleton;

@Singleton
public class FakeTaskDBHandler implements TaskDBHandler{

	private List<TaskTemplate> templatesInTimeSlot = new ArrayList<TaskTemplate>();
	private List<TaskTemplate> templatesAdded = new ArrayList<TaskTemplate>();
    private List<TaskTemplate> templateDb = new ArrayList<TaskTemplate>();
	private boolean failOperation = false;


    public ListenableFuture<List<TaskTemplate>> fetchTemplatesForTimeslot(int startUnix, int endUnix){

    	if(!failOperation){
    		return Futures.immediateFuture(templatesInTimeSlot);
    	}else{
    		throw new java.lang.UnsupportedOperationException("Fake DB fetch error");
    	}
    }

    public ListenableFuture<List<TaskTemplate>> insertNewTemplates(List<TaskTemplate> templatesToInsert){
    	if(!failOperation){
            List<TaskTemplate> updatedTemplates = 
                templatesToInsert.stream()
                    .map( template -> template.toBuilder()
    			                         .setTemplateId((new Random()).nextInt(100))
                                        .build())
                    .collect(toList());
    		templatesAdded.addAll(updatedTemplates);
    		return Futures.immediateFuture(updatedTemplates);
    	}else{
    		throw new java.lang.UnsupportedOperationException("Fake DB Insert error");
    	}
    }

    public ListenableFuture<Void> alterTemplate(int templateId, Optional<Integer> newEndTime,Optional<String> descriptionAlteration, Optional<List<Integer>> newDataCollectionIds ){

        if(!failOperation){
            templateDb = templateDb.stream().map(template -> template.getTemplateId() == templateId ? updateTemplate(template, newEndTime, descriptionAlteration, newDataCollectionIds) : template).collect(toList());
            return Futures.immediateFuture(null);
        }else{
            throw new java.lang.UnsupportedOperationException("Fake DB alter error");
        }
    }

    private TaskTemplate updateTemplate(TaskTemplate template, Optional<Integer> newEndTime,Optional<String> descriptionAlteration, Optional<List<Integer>> newDataCollectionIds) {

        TaskTemplate.Builder builder = template.toBuilder();

        if(newEndTime.isPresent()){
            builder.getTimeConfigurationBuilder().setEndTimestamp(newEndTime.get());
        }

        if(descriptionAlteration.isPresent()){
            builder.getDetailsBuilder().setDescription(descriptionAlteration.get());
        }

        if(newDataCollectionIds.isPresent()){
            builder.clearDataCollectionId().addAllDataCollectionId(newDataCollectionIds.get());
        }

        return builder.build();

    }

    public void reset() {
        templatesAdded = new ArrayList<TaskTemplate>();
    	templatesInTimeSlot = new ArrayList<TaskTemplate>();
        templateDb = new ArrayList<TaskTemplate>();
    	failOperation = false;
    }

    public void setTemplatesToReturnInTimeslot(List<TaskTemplate> templatesInTimeSlot){
    	this.templatesInTimeSlot = templatesInTimeSlot;
    }

    public void setTemplateDb(List<TaskTemplate> templateDb){
        this.templateDb = templateDb;
    }

    public void setFailOperation(boolean failOperation){
    	this.failOperation = failOperation;
    }

    public List<TaskTemplate> getTemplatesAdded(){
        return templatesAdded;
    }

    public List<TaskTemplate> getTemplateDb(){
        return templateDb;
    }

}
