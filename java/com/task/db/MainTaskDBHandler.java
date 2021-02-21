package com.task.db;

import static java.util.stream.Collectors.toList;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import com.google.common.util.concurrent.Futures;
import com.task.TaskProto.TaskEntry;
import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.Project;
import com.task.TaskProto.TimeAlteractionPolicy;
import com.task.TaskDBProto.DBFetchTemplateRequest;
import com.task.TaskDBProto.DBFetchEntriesRequest;
import com.task.TaskDBProto.UpdateDBEntryRequest;
import com.task.TaskDBProto.DBFetchProjectsRequest;
import com.google.common.util.concurrent.ListenableFuture;
import com.task.SampleTasksUtil;

public class MainTaskDBHandler implements TaskDBHandler{

    public ListenableFuture<List<TaskTemplate>> fetchTemplates(DBFetchTemplateRequest fetchTemplateRequest){
    	return Futures.immediateFuture(SampleTasksUtil.PROD_TEMPLATES);
    }

    public ListenableFuture<List<TaskTemplate>> insertNewTemplates(List<TaskTemplate> templatesToInsert){
    	return Futures.immediateFuture(templatesToInsert.stream().map(template -> template.toBuilder().setTemplateId((new Random()).nextInt(100)).build()).collect(toList()));
    }

    public ListenableFuture<Void> alterTemplate(int templateId, Optional<Integer> newEndTime,Optional<String> descriptionAlteration, Optional<List<Integer>> newDataCollectionIds ){
    	return Futures.immediateFuture(null);
    }

    public ListenableFuture<List<TaskEntry>> fetchEntries(DBFetchEntriesRequest fetchEntriesRequest){
     	return Futures.immediateFuture(new ArrayList<TaskEntry>());
    }

    public ListenableFuture<Void> alterEntry(UpdateDBEntryRequest updateDBEntryRequest){
        return Futures.immediateFuture(null); 
    }

    public ListenableFuture<List<Project>> fetchProjects(DBFetchProjectsRequest fetchProjectsRequest){
        return Futures.immediateFuture(new ArrayList<Project>());
    }

}
