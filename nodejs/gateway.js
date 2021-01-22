var grpc = require('@grpc/grpc-js');
const express = require('express')

const app = express()
const port = 3000


const templateServiceProto = require('./template_nodejs_proto_pb/proto/template/template_grpc_pb.js')
const templateProto = require('./template_nodejs_proto_pb/proto/template/template_pb.js')

var client = new templateServiceProto.TemplateServiceClient("template", grpc.credentials.createInsecure()); 

app.get('/', (req, res) => {
  console.log("generic rest  recieved")
  res.send('this is the GATEWAY!')
})

var testRequest = new templateProto.GenerateScheduleRequest().setScheduleStartUnix(1610240520)
                    .setScheduleEndUnix(1610388800);

app.get('/testgrpc', (req, res) => {
  console.log("testgrpc recieved")
    client.generateScheduleFromTemplates(testRequest, (err, data) => {
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




