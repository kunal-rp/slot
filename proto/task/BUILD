package(default_visibility = ["//visibility:public"])

load("@rules_proto//proto:defs.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_proto_library")
load("@rules_proto_grpc//java:defs.bzl", "java_grpc_library")


proto_library(
    name = "task_proto",
    srcs = ["task.proto"],
)

java_proto_library(
    name = "task_java_proto",
    deps = [":task_proto"],
)

proto_library(
    name = "task_db_proto",
    srcs = ["db.proto"],
    deps = [":task_proto"]
)

java_proto_library(
    name = "task_db_java_proto",
    deps = [":task_db_proto"],
)


proto_library(
    name = "task_service_proto",
    srcs = ["task_service.proto"],
    deps = [":task_proto"]
)

java_grpc_library(
    name = "task_service_java_proto",
    deps = [":task_service_proto"],
)
