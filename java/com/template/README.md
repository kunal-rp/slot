# template Service

All services regarding generation of schedules and evaludations of tasks. 

## API


| NAME | DESCRIPTION | PARAMS | OUTPUT |
 ---
 | GenerateScheduleEntries | For a given time slot generates task entries based on task templates | (int) slot unix start time  , (int) slot unix end time | List<TaskEnty> |