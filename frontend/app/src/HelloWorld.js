import React, {useState, useEffect} from 'react';
import Button from '@material-ui/core/Button';

import Employee from './Employee';

const TASK_PROTO_PATH = '/proto/task/task_nodejs_service_proto_pb';
const test = "123"
const taskServiceProto = require(TASK_PROTO_PATH+'/task_service_grpc_pb.js')
const taskProto = require(TASK_PROTO_PATH+'/task_service_pb.js')

console.log("startup");
console.log("startup");

var client = new taskServiceProto.TaskServiceClient("task:80", grpc.credentials.createInsecure()); 

const HelloWorld = () => {

	const [employeeList, setEmployeeList] = useState("");

	useEffect(() => {

		var startTimeUnix = 1610240520 // 1/10/21
		var endTimeUnix = () => startTimeUnix + (getRand(15, 40) * 86400 )+ (getRand(0, 24) * 3600 )
		var genRequest = () => 
			new taskProto.GenerateScheduleRequest()
				.setScheduleStartUnix(startTimeUnix)
				.setScheduleEndUnix(endTimeUnix());
		client.generateSchedule(request, (err, data) => {
		      if(err){
		        console.log(err)
		        return
		      }
		      console.log("testgrpc response")
		      setEmployeeList(data.toObject().toString())
		    })
	});


  return (
  		<div>
	     	<h3>Hello this is a test  </h3>
	     	<Button variant="contained" color="primary" onClick={() => { console.log('clicked') }}>
		     	Hello World
		   	</Button>
		   	<div>
		   	{employeeList.map(emp => <Employee name={emp.employee_name}/>)}
		   	</div>
	    </div>
  );
};

export default HelloWorld;