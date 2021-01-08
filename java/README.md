# Backend

gRPC servers mainly written in Java

## General Design

I would like to incorperate generation into task managment, specifically for current/future tasks.

This goes hand in hand with Univeral Rule, prioritizing to get accurate logs. Also will help with managment of tasks when viewing schedules for future dates

Task Templates are stored w/ information about what task details are; what is due date/pattern, what data is required(seperate db); this DB will be used to generate the tasks for a schedule in the future. 

Per day(or time interval), a cron job will parse and run said templates to generate the tasks that were made to be due in the next 12 hours, call this the timeslot, for modifications; creating them, inputting them in entries db, and marking them as due/not-completed.To determine tasks that must immediatly go through completion flow, just need to parse for non completed tasks w/ start times in the past.

**The assumption can then be made that all tasks within timezone are editable 

Task Entry ID Generation : 
- One Time Task : same as template id
- Reoccuring Task : template_id.ORIGIONAL_START_TIME

Altering Timings of Task:
- One Time : 
    * alter the template
    * delete all entries w/ same template id, recreate
- Reoccuring, Altering single occurance : 
    * get the original start time of single occurance of task 
    * get corresponding

Upon user completed a task and inputting data would either: 
- updating the existing task entry w/ flag and data
- create a new entry w/ flag and data


## DB's

There will be two db's for tasks, Templates and Entries. 

Task Templates( one to one): 
- template_id : int
- creation time : int ( unix)
- title : string
- duration : int
- description : string
- project_id : int
- reoccuring pattern : string 
- due date

Per design, task should either have reoccuring pattern or due date. 

Task Data Collection ( one to many): 
- template_id : int
- data_label : string
- data_value_type : string [INT, STRING, BOOLEAN]

Task Entry: 
- task_id : int
- template_id : int
- origional_start_time : int (unix)
- start_time : int (unix)
- duration : int
- completed : boolean
- json_data : string

json_data would be stringified values inputted by user matching ones set per templates in Task Data DB.




