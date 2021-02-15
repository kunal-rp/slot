package com.task.db;

import java.util.Optional;
import java.util.List;
import com.task.TaskProto.TaskEntry;
import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.Project;
import com.task.TaskProto.TimeAlteractionPolicy;
import com.task.TaskDBProto.DBFetchEntriesRequest;
import com.task.TaskDBProto.UpdateDBEntryRequest;
import com.task.TaskDBProto.DBFetchProjectsRequest;
import com.google.common.util.concurrent.ListenableFuture;

/* 
	DB Util for all task related operations  
*/ 
public interface TaskDBHandler {

	//Template
    public ListenableFuture<List<TaskTemplate>> fetchTemplatesForTimeslot(int startUnix, int endUnix);

    public ListenableFuture<List<TaskTemplate>> insertNewTemplates(List<TaskTemplate> templatesToInsert);

    public ListenableFuture<Void> alterTemplate(int templateId, Optional<Integer> newEndTime,Optional<String> descriptionAlteration, Optional<List<Integer>> newDataCollectionIds );

    public ListenableFuture<List<TaskEntry>> fetchEntries(DBFetchEntriesRequest fetchEntriesRequest);

    public ListenableFuture<Void> alterEntry(UpdateDBEntryRequest updateDBEntryRequest);

    public ListenableFuture<List<Project>> fetchProjects(DBFetchProjectsRequest fetchProjectsRequest);
}
