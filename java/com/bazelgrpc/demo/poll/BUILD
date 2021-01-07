load("@rules_java//java:defs.bzl","java_library")

package(default_visibility = ["//visibility:public"])

# The java_binary rule, like many others, has multiple outputs. We'll
# see that in action shortly.

java_library(
    name = "poll-util",
    srcs = ["PollUtil.java"],
    deps = [
        "//proto/poll:poll_java_proto",
    ],
)

java_library(
    name = "poll-service-impl",
    srcs = ["PollManagementImpl.java"],
    deps = [
        ":poll-util",
        "//proto/poll:poll_service_java_proto",
    ],
)

java_library(
    name = "poll-client",
    srcs = ["PollClient.java"],
    deps = [
       "//proto/poll:poll_service_java_proto",
        "//java/com/bazelgrpc/demo/util:setup-util"
    ],
)

java_library(
    name = "poll-handler",
    srcs = glob(["*Handler.java"]),
    deps = [
        "//proto/poll:poll_java_proto",
        ":poll-client",
         "@maven//:com_google_inject_guice",
    ],
)

java_binary(
    name = "RunPollClient",
    srcs = ["RunPollClient.java"],
    deps = [
        ":poll-client"
    ],
)