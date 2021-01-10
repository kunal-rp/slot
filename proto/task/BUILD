package(default_visibility = ["//visibility:public"])

load("@rules_proto//proto:defs.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_proto_library")


proto_library(
    name = "task_proto",
    srcs = ["task.proto"],
)

java_proto_library(
    name = "task_java_proto",
    deps = [":task_proto"],
)
