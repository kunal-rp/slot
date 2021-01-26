package com.task.db;

import java.util.List;
import java.util.concurrent.Future;
import com.task.TaskProto.TaskTemplate;

/* 
	DB Util for all task related operations  
*/ 
public interface TaskDBHandler {

    public List<TaskTemplate> fetchTemplatesForTimeslot(int startUnix, int endUnix);

    public Future<TaskTemplate> insertNewTemplate(TaskTemplate templateDetailsToInsert);

    public void deleteTemplate(int templateId); 

}
