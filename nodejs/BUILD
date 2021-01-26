package(default_visibility = ["//visibility:public"])

load("@rules_proto_grpc//nodejs:defs.bzl", "nodejs_grpc_library", "nodejs_proto_library")
load("@io_bazel_rules_docker//nodejs:image.bzl", "nodejs_image")

nodejs_grpc_library(
    name = "task_nodejs_service_proto",
    deps = ["//proto/task:task_service_proto"],
)


nodejs_image(
    name = "nodejs_gateway_image",
     entry_point = "gateway.js",
     data = [
      "@nodejs_modules//express",
      "@nodejs_modules//@grpc/grpc-js",
      ":task_nodejs_service_proto"
    ],
)