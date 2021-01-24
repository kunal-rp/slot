package(default_visibility = ["//visibility:public"])

load("@rules_proto_grpc//nodejs:defs.bzl", "nodejs_grpc_library")
load("@build_bazel_rules_nodejs//:index.bzl", "nodejs_binary")
load("@io_bazel_rules_docker//nodejs:image.bzl", "nodejs_image")
load("@io_bazel_rules_docker//container:container.bzl", "container_image")
load("//ci:service_configs.bzl","GATEWAY_REPO","ECR_URL")

nodejs_grpc_library(
    name = "template_nodejs_proto",
    deps = ["//proto/template:template_service_proto"],
)


nodejs_image(
    name = "nodejs_gateway_image",
     entry_point = "gateway.js",
     data = [
      "@nodejs_modules//express",
      "@nodejs_modules//@grpc/grpc-js",
      ":template_nodejs_proto"
    ],
)

# create container image
container_image(
    name = "gateway_image",
    base = ":nodejs_gateway_image",
)