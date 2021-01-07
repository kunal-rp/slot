package(default_visibility = ["//visibility:public"])

load("@rules_proto//proto:defs.bzl", "proto_library")
load("@rules_proto_grpc//java:defs.bzl", "java_grpc_library")

proto_library(
    name = "action_service_proto",
    srcs = ["action_service.proto"],
    deps = [
        "//proto/poll:poll_proto",
        "//proto/annotate:annotate_proto"
    ],
)

java_grpc_library(
    name = "action_service_java_proto",
    deps = [":action_service_proto"],
)