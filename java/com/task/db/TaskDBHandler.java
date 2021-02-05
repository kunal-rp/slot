package com.task.db;

import java.util.Optional;
import java.util.List;
import com.task.TaskProto.TaskTemplate;
import com.task.TaskProto.TimeAlteractionPolicy;
import com.google.common.util.concurrent.ListenableFuture;

/* 
	DB Util for all task related operations  
*/ 
public interface TaskDBHandler {

	//Template
    public ListenableFuture<List<TaskTemplate>> fetchTemplatesForTimeslot(int startUnix, int endUnix);

    public ListenableFuture<List<TaskTemplate>> insertNewTemplates(List<TaskTemplate> templatesToInsert);

    public ListenableFuture<Void> alterTemplate(int templateId, Optional<Integer> newEndTime,Optional<String> descriptionAlteration, Optional<List<Integer>> newDataCollectionIds );

}
