package com.task.db;

import static java.util.stream.Collectors.toList;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import com.google.common.util.concurrent.Futures;
import com.task.TaskProto.TaskEntry;
import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.TimeAlteractionPolicy;
import com.task.TaskDBProto.DBFetchEntriesRequest;
import com.task.TaskDBProto.UpdateDBEntryRequest;
import com.google.common.util.concurrent.ListenableFuture;
import com.task.SampleTasksUtil;

public class MainTaskDBHandler implements TaskDBHandler{

    public ListenableFuture<List<TaskTemplate>> fetchTemplatesForTimeslot(int startUnix, int endUnix){
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

}
