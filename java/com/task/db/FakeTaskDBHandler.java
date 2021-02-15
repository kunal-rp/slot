package com.task.db;

import static java.util.stream.Collectors.toList;

import java.util.Random;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import com.google.common.util.concurrent.Futures;
import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.TaskEntry;
import com.task.TaskProto.TimeAlteractionPolicy;
import com.task.TaskDBProto.DBFetchEntriesRequest;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Singleton;

@Singleton
public class FakeTaskDBHandler implements TaskDBHandler{

    // Template 
	private List<TaskTemplate> templatesInTimeSlot = new ArrayList<TaskTemplate>();
	private List<TaskTemplate> templatesAdded = new ArrayList<TaskTemplate>();
    private List<TaskTemplate> templateDb = new ArrayList<TaskTemplate>();

    //Task
    private List<TaskEntry> entryDb = new ArrayList<TaskEntry>();
	private boolean failOperation = false;

    @Override
    public ListenableFuture<List<TaskTemplate>> fetchTemplatesForTimeslot(int startUnix, int endUnix){

    	if(!failOperation){
    		return Futures.immediateFuture(templatesInTimeSlot);
    	}else{
    		throw new java.lang.UnsupportedOperationException("Fake DB fetch error");
    	}
    }

    @Override
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

    @Override
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

    @Override
    public ListenableFuture<List<TaskEntry>> fetchEntries(DBFetchEntriesRequest fetchEntriesRequest){
        return Futures.immediateFuture(
            entryDb.stream().filter(entry ->shouldSelectEntry(entry, fetchEntriesRequest) ).collect(toList()));

    }

    private boolean shouldSelectEntry(TaskEntry taskEntry, DBFetchEntriesRequest fetchEntriesRequest){

        int entryStartTimestamp  = (int) taskEntry.getStartTimestamp();
        int entryDuration = (int) taskEntry.getDuration();

        if(taskEntry.hasTimeAlterations() && fetchEntriesRequest.getIncludeTimeAlterations()){
            entryStartTimestamp += taskEntry.getTimeAlterations().getStartTimestampSurplus();
            entryDuration += taskEntry.getTimeAlterations().getDurationSurplus();
        }

        boolean entryStartDuringTimeslot = fetchEntriesRequest.getStartingUnix() <= entryStartTimestamp && entryStartTimestamp < fetchEntriesRequest.getEndingUnix();
        boolean entryEndsDuringTimeslot = fetchEntriesRequest.getEndingUnix() > entryStartTimestamp + entryDuration && entryStartTimestamp + entryDuration  >= fetchEntriesRequest.getEndingUnix();

        if(!(entryStartDuringTimeslot || entryEndsDuringTimeslot)){
            return false;
        }

        boolean matchesValidTempateIdAndOccurance = fetchEntriesRequest.getValidTemplateIdList()
            .stream().filter(templateIdAndOccurance -> 
            taskEntry.getTemplateId() == templateIdAndOccurance.getTemplateId() &&
             taskEntry.getOccurance() == templateIdAndOccurance.getOccurance()).findFirst().isPresent();

        boolean matchesInvalidTempateIdAndOccurance = fetchEntriesRequest.getInvalidTemplateIdList()
            .stream().filter(templateIdAndOccurance -> 
            taskEntry.getTemplateId() == templateIdAndOccurance.getTemplateId() &&
             taskEntry.getOccurance() == templateIdAndOccurance.getOccurance()).findFirst().isPresent();

        if((!fetchEntriesRequest.getValidTemplateIdList().isEmpty() && !matchesValidTempateIdAndOccurance ) || 
            (!fetchEntriesRequest.getInvalidTemplateIdList().isEmpty() && matchesInvalidTempateIdAndOccurance ) ){
            return false;
        }

        return true;
    }

    public void reset() {
        templatesAdded = new ArrayList<TaskTemplate>();
    	templatesInTimeSlot = new ArrayList<TaskTemplate>();
        templateDb = new ArrayList<TaskTemplate>();
        entryDb = new ArrayList<TaskEntry>();
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

    public void setEntryDb(List<TaskEntry> entryDb){
        this.entryDb = entryDb;
    }

    public List<TaskTemplate> getTemplatesAdded(){
        return templatesAdded;
    }

    public List<TaskTemplate> getTemplateDb(){
        return templateDb;
    }
}
