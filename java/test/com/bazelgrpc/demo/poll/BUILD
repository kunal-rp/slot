load("//:java/test/java_testing_generator.bzl", "java_unit_test")

package(default_visibility = ["//visibility:public"])

java_unit_test(
    name = "poll_unit_test",
    pkg="com.bazelgrpc.demo.poll",
    srcs = glob(["*.java"]),
    deps = [
        "//proto/poll:poll_service_java_proto",
        "//proto/poll:poll_java_proto",
    	"//java/com/bazelgrpc/demo/poll:poll-util",
    	"//java/com/bazelgrpc/demo/poll:poll-service-impl",
    	"@io_grpc_grpc_java//api",
        "@io_grpc_grpc_java//core:inprocess",
        "@maven//:io_grpc_grpc_testing",
    ],

)
