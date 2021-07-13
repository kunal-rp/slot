const LOCAL_PATH = './genProto/proto/task/'
const BAZEL_PATH = './proto/task/'

const PATH = process.env.LOCAL_URL ? LOCAL_PATH : BAZEL_PATH

const taskServiceProto = require(PATH+'task_service_grpc_web_pb.js')


module.exports = {
	getTaskServiceProto: () => taskServiceProto,
	getTaskServiceClient: () => new taskServiceProto.TaskServiceClient(
			(process.env.LOCAL_URL ? 
			process.env.LOCAL_URL : 
			"http://slot.appp/gapi" ) )
}