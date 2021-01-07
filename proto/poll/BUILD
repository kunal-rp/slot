package(default_visibility = ["//visibility:public"])

load("@rules_proto//proto:defs.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_proto_library")
load("@rules_proto_grpc//java:defs.bzl", "java_grpc_library")


proto_library(
    name = "poll_proto",
    srcs = ["poll.proto"],
)

java_proto_library(
    name = "poll_java_proto",
    deps = [":poll_proto"],
)


proto_library(
    name = "poll_service_proto",
    srcs = ["poll_service.proto"],
    deps = [":poll_proto"],
)

java_grpc_library(
    name = "poll_service_java_proto",
    deps = [":poll_service_proto"],
)