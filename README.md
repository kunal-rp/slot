# slot
Time Bloc Managment

- create reoccuring/pattern based tasks
- create one of tasks
- denominate into sub tasks
- force completion/closing of previous day's task
- easy to indicate task in progress and schedule time later to complete

**The Main purpose is keepting track of tasks, collection of basic data per task, and allocating time**
No linking for Google Calendars for now 


## Task : Single item of work 

Tasks Parts: 
- Details : Time Type, Duration( never 0, at least 5 minutes), Description
    * Task can be created w/out time type specified, will be labeled as 'Future Work'
- Project: Link to project group
- Task Data: Data to be collected to complete task ( is always optionally collected, s.t. fake data entry is not forced)
- Parent: Linked task that current one is derived from ( mainly used in cases of incompletion)

Time Type:
- One Time
- Reoccuring
- Future Work
    * No time allocation yet, but in queue to do 

**The Universal Rule is that to complete a task, it must first be allocated time to do.** This is to get accurate info on where time is spent and better plan day out. 

Reoccuring Patterns: 
- Denomincations: Month(Day # in month),Week(Day of Week),Day(Interval days), Hours/Minutes
- i.e.:
    * second day of week , take out the trash
    * first day of month, pay utilities
    * starting from 1/1/21, every third day, do pull day exercises
    * everday, at X time, call someone new

Completion: 
- Pending: Task is not completed
- Finished: Single item of Work is finished
- Incomplete : Work left to do for task 
    * This will mark the current task as finished & create a new task w/ origional as parent


## Projects : Grouping Tasks together

Project Parts: 
- Details : Title, Description
- Universal Data : Data to be collected in all tasks under subgroup, any time type
- Sub Project : Projects can be nested
    * i.e. Workout/Exercise contains sub projects Push/Pull/Legs/Cardio Projects, which each contain reoccuring tasks & one-time tasks of playing basketball manually added randomly
    * i.e. Side Projects contain sub projects for each indiv. project w/ tasks

## Task Creation Flows

**Prioritiy over tying loose ends on past tasks**
Flow should activley check to ensure past tasks go through completion flow, regarless of finished or pending, before entertaining new task creation. Regartless of task type, project, time type; if a task is created in the past that hasn't gone through completion, stop everything and force user to do it.Again, this is to ensure accurate logging of tasks.

Assuming there are no outstanding tyding up, the different ways to add a task at a particular time:

New One Time Task : 
- Create new task , fill all parts
- At the end , fill out data and mark completion

Pending -> Working Task:
- Update task w/ time type
- At the end, fill out data dn mark completion

## Schedule Modification

Flexibility in altering the immediate schedule is imparative. Per interval enon job, backend will generate task entries per templates for tasks that are in immediate timeslot, denoting the supposed start time and duration for each.

This will allow for ability to create new tasks immediatly and add to schedule while altering start time/duration of task with ease.

For tasks that are in the future beyone time slot, task entry will be created w/ alterations. 


# Server Driven UI 

All API's will be returned w/ data on how the page will need to be structured, allowing for better control accross all clients.

Frontend apps (web and mobile) will only need to implement the different rendering objects and recreate the page based response
