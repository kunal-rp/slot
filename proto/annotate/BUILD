package(default_visibility = ["//visibility:public"])

load("@rules_proto//proto:defs.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_proto_library")
load("@rules_proto_grpc//java:defs.bzl", "java_grpc_library")


proto_library(
    name = "annotate_proto",
    srcs = ["annotate.proto"],
)

java_proto_library(
    name = "annotate_java_proto",
    deps = [":annotate_proto"],
)

proto_library(
    name = "annotate_service_proto",
    srcs = ["annotate_service.proto"],
    deps = [":annotate_proto"],
)

java_grpc_library(
    name = "annotate_service_java_proto",
    deps = [":annotate_service_proto"],
)