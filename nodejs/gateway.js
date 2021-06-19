var grpc = require('@grpc/grpc-js');
const express = require('express')

const app = express()
const port = 3000

//const TASK_PROTO_PATH = process.cwd()+'/proto/task/task_nodejs_service_proto_pb/proto/task';

const taskServiceProto = require(TASK_PROTO_PATH+'/task_service_grpc_pb.js')
const taskProto = require(TASK_PROTO_PATH+'/task_service_pb.js')

var client = new taskServiceProto.TaskServiceClient("task:80", grpc.credentials.createInsecure()); 

app.get('/', (req, res) => {
  console.log("generic rest  recieved")
  res.send('this is the GATEWAY!')
})

var startTimeUnix = 1610240520 // 1/10/21
var endTimeUnix = () => startTimeUnix + (getRand(15, 40) * 86400 )+ (getRand(0, 24) * 3600 )
var genRequest = () => new taskProto.GenerateScheduleRequest()
                .setScheduleStartUnix(startTimeUnix)
                    .setScheduleEndUnix(endTimeUnix());
console.log(endTimeUnix)

app.get('/testgrpc', (req, res) => {
  console.log("testgrpc recieved")
    var request = genRequest()
    console.log(request)
    console.log(new Date(request.getScheduleEndUnix() * 1000))
    client.generateSchedule(request, (err, data) => {
      if(err){
        res.json({err: err})
        return
      }
      console.log("testgrpc response")
        res.json({data : data.toObject(), err: err})
    })
  })

app.listen(port, () => {
  console.log("gateway server running")
})




function getRand(min, max) {
  return Math.trunc(Math.random() * (max - min) + min);
}

