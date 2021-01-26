load("@rules_java//java:defs.bzl","java_library")
load("@io_bazel_rules_docker//container:container.bzl", "container_image")
load("@io_bazel_rules_docker//java:image.bzl", "java_image")

package(default_visibility = ["//visibility:public"])


java_library(
    name = "generative_schedule_util",
    srcs = ["GenerativeScheduleUtil.java"],
    deps = [
         "//proto/task:task_java_proto",
        "//java/com/task:sample_tasks_util",
    ],
)