load("@rules_java//java:defs.bzl", "java_library")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "action-service-impl",
    srcs = ["ActionManagementImpl.java"],
    deps = [
        "//java/com/bazelgrpc/demo/poll:poll-handler",
        "//java/com/bazelgrpc/demo/util:setup-util",
        "//proto/action:action_service_java_proto",
        "@maven//:com_google_inject_guice",
    ],
)

java_binary(
    name = "ActionClient",
    srcs = ["ActionClient.java"],
    deps = [
        "//java/com/bazelgrpc/demo/util:setup-util",
        "//proto/action:action_service_java_proto",
    ],
)
