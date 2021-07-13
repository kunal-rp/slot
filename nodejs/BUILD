package(default_visibility = ["//visibility:public"])

load("@rules_proto_grpc//js:defs.bzl", "js_grpc_node_library")
load("@io_bazel_rules_docker//nodejs:image.bzl", "nodejs_image")

js_grpc_node_library(
    name = "task_nodejs_service_proto",
    deps = ["//proto/task:task_service_proto"],
    deps_repo = "@nodejs_modules"
)


nodejs_image(
    name = "nodejs_gateway_image",
     entry_point = "gateway.js",
     data = [
      "@nodejs_modules//express",
      #"@nodejs_modules//@grpc/grpc-js",
      ":task_nodejs_service_proto"
    ],
)
