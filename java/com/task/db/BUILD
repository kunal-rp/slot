load("@rules_java//java:defs.bzl","java_library")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "task_db_handler",
    srcs = ["TaskDBHandler.java"],
    deps = [
    "@maven//:com_google_guava_guava",
        "//proto/task:task_java_proto",
    ],
)

java_library(
    name = "main_task_db_handler",
    srcs = ["MainTaskDBHandler.java"],
    deps = [
        "@maven//:com_google_guava_guava",
        ":task_db_handler",
        "//proto/task:task_java_proto",
        "//java/com/task:sample_tasks_util"
    ],
)

java_library(
    name = "fake_task_db_handler",
    srcs = ["FakeTaskDBHandler.java"],
    deps = [
        "@maven//:com_google_inject_guice",
        "@maven//:com_google_guava_guava",
        ":task_db_handler",
        "//proto/task:task_java_proto",
    ],
)