package(default_visibility = ["//visibility:public"])

load("@rules_proto//proto:defs.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_proto_library")
load("@rules_proto_grpc//java:defs.bzl", "java_grpc_library")


proto_library(
    name = "template_service_proto",
    srcs = ["template.proto"],
    deps = ["//proto/task:task_proto"]
)

java_grpc_library(
    name = "template_service_java_proto",
    deps = [":template_service_proto"],
)