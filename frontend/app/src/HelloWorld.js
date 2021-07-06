import React, {useState, useEffect} from 'react';
import Button from '@material-ui/core/Button';
import Employee from './Employee';


console.log("start")
const taskServiceProto = require('./proto/task/task_service_grpc_web_pb.js')
console.log(taskServiceProto)

var client = new taskServiceProto.TaskServiceClient("task:80/gateway"); 

function getRand(min, max) {
  return Math.trunc(Math.random() * (max - min) + min);
}

const HelloWorld = () => {

	const [employeeList, setEmployeeList] = useState([]);

	
	useEffect(() => {

		var startTimeUnix = 1610240520 // 1/10/21
		var endTimeUnix = () => startTimeUnix + (getRand(15, 40) * 86400 )+ (getRand(0, 24) * 3600 )
		var request = new taskServiceProto.GenerateScheduleRequest()
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
	
	{employeeList.map(emp => <Employee name={emp.employee_name}/>)}
	


  return (
  		<div>
	     	<h3>Hello this is a test  </h3>
	     	<Button variant="contained" color="primary" onClick={() => { console.log('clicked') }}>
		     	Hello World
		   	</Button>
		   	<div>
		   	
		   	</div>
	    </div>
  );
};

export default HelloWorld;