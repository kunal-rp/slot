load("//:java/test/java_testing_generator.bzl", "java_unit_test")

package(default_visibility = ["//visibility:public"])

java_unit_test(
    name = "action_unit_test",
    pkg="com.bazelgrpc.demo.action",
    srcs = glob(["*.java"]),
    deps = [
        "//proto/action:action_service_java_proto",
        "//java/com/bazelgrpc/demo/action:action-service-impl",
    	"@io_grpc_grpc_java//api",
        "@io_grpc_grpc_java//core:inprocess",
        '//java/com/bazelgrpc/demo/poll:poll-handler',
        "@maven//:io_grpc_grpc_testing",
        "//proto/poll:poll_java_proto",
        "@maven//:com_google_inject_guice",
    ],

)
