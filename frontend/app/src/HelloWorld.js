import React, {useState, useEffect} from 'react';
import Button from '@material-ui/core/Button';
import Task from './Task';
import {getTaskServiceProto, getTaskServiceClient} from './protoHelper';

const taskServiceProto = getTaskServiceProto()

var client = getTaskServiceClient(); 

function getRand(min, max) {
  return Math.trunc(Math.random() * (max - min) + min);
}

const HelloWorld = () => {

	const [taskList, setTaskList] = useState([]);

	useEffect(() => {

	});

	function getSchedule(){
		var startTimeUnix = 1610240520 // 1/10/21
		var endTimeUnix = () => startTimeUnix + (getRand(15, 40) * 86400 )+ (getRand(0, 24) * 3600 )
		var request = new taskServiceProto.GenerateScheduleRequest();
		request.setScheduleStartUnix(startTimeUnix);
		request.setScheduleEndUnix(endTimeUnix());
		client.generateSchedule(request, {},(err, data) => {
		      if(err){
		        console.log(err)
		        return
		      }
		      console.log("testgrpc response")
		      setTaskList(data.getGeneratedTaskEntryList())
		    })

	}
	
	function getTasks(){
		return taskList.map(task => <Task task={task}/> )
	}

  return (
  		<div>
	     	<h3>Hello this is a test  </h3>
	     	<Button variant="contained" color="primary" onClick={() => { getSchedule() }}>
		     	Hello World
		   	</Button>
		   	<div>
		   		{getTasks()}
		   	</div>
	    </div>
  );
};

export default HelloWorld;