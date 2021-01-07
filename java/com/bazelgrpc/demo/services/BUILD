load("@rules_java//java:defs.bzl","java_binary")
load("@io_bazel_rules_docker//container:container.bzl", "container_image","container_push")
load("@io_bazel_rules_docker//java:image.bzl", "java_image")
load("//ci:service_configs.bzl","ACTION_REPO", "POLL_REPO", "ECR_URL")

package(default_visibility = ["//visibility:public"])


java_library(
    name = "service-module",
    srcs = ["ServiceModule.java"],
    deps = [
       "//proto/poll:poll_service_java_proto",
        "//java/com/bazelgrpc/demo/util:setup-util",
         "@maven//:com_google_inject_guice",
         "//java/com/bazelgrpc/demo/poll:poll-handler",
    ],
)


# ACTION SERVIE 

java_image(
    name = "java_action_image",
     srcs = ["ActionService.java"],
     deps = [
        "//java/com/bazelgrpc/demo/action:action-service-impl",
        "//java/com/bazelgrpc/demo/util:setup-util",
        "@io_grpc_grpc_java//api",
        "@maven//:com_google_guava_guava",
         ":service-module",
          "@maven//:com_google_inject_guice",
     ],
    main_class = "com.bazelgrpc.demo.services.ActionService",
)

# create container image
container_image(
    name = "action_image",
    base = "java_action_image",
    creation_time = "{BUILD_TIMESTAMP}",
    stamp = True,
)


container_push(
   name = "push_action_image",
   image = ":action_image",
   format = "Docker",
   registry = ECR_URL,
   repository = ACTION_REPO,
   tag = "dev"
)


# POLL SERVIE 

java_image(
    name = "java_poll_image",
     srcs = ["PollService.java"],
     deps = [
        "//java/com/bazelgrpc/demo/poll:poll-service-impl",
        "//java/com/bazelgrpc/demo/util:setup-util",
        "@io_grpc_grpc_java//api",
        "@maven//:com_google_guava_guava",
     ],
    main_class = "com.bazelgrpc.demo.services.PollService",
)

# create container image
container_image(
    name = "poll_image",
    base = "java_poll_image",
    creation_time = "{BUILD_TIMESTAMP}",
    stamp = True,
)


container_push(
   name = "push_poll_image",
   image = ":poll_image",
   format = "Docker",
   registry = ECR_URL,
   repository = POLL_REPO,
   tag = "dev"
)
