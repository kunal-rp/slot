package(default_visibility = ["//visibility:public"])

load("@io_bazel_rules_docker//nodejs:image.bzl", "nodejs_image")


nodejs_image(
    name = "nodejs_gateway_image",
     entry_point = "gateway.js",
     data = [
      "@nodejs_modules//express",
      "@nodejs_modules//@grpc/grpc-js",
      "//proto/task:task_nodejs_service_proto"
    ],
)