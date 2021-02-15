var grpc = require('@grpc/grpc-js');
const express = require('express')

const app = express()
const port = 3000


const taskServiceProto = require('./task_nodejs_service_proto_pb/proto/task/task_service_grpc_pb.js')
const taskProto = require('./task_nodejs_service_proto_pb/proto/task/task_service_pb.js')

var client = new taskServiceProto.TaskServiceClient("task:80", grpc.credentials.createInsecure()); 

app.get('/', (req, res) => {
  console.log("generic rest  recieved")
  res.send('this is the GATEWAY!')
})

var testRequest = new taskProto.GenerateScheduleRequest().setScheduleStartUnix(1610240520) // 1/10/21
                    .setScheduleEndUnix(1612915200); // 2/10/21

app.get('/testgrpc', (req, res) => {
  console.log("testgrpc recieved")
    client.generateSchedule(testRequest, (err, data) => {
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




