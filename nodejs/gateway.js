var grpc = require('@grpc/grpc-js');
const express = require('express')

const app = express()
const port = 3000
const actionServiceProto = require('./action_service_nodejs_proto_pb/proto/action/action_service_grpc_pb.js')
const actionProto = require('./action_service_nodejs_proto_pb/proto/action/action_service_pb.js')

var request = new actionProto.InitializePageRequest();

var client = new actionServiceProto.ActionManagementClient("action-bazelgrpc:80", grpc.credentials.createInsecure()); 


app.get('/', (req, res) => {
  console.log("generic rest  recieved")
  res.send('this is the GATEWAY!')
})

app.get('/testgrpc', (req, res) => {
  console.log("testgrpc recieved")
    client.initializePage(new actionProto.InitializePageRequest(), (err, data) => {
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