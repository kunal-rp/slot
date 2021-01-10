load("@rules_java//java:defs.bzl","java_library")

package(default_visibility = ["//visibility:public"])


java_library(
    name = "schedule_handler",
    srcs = ["ScheduleHandler.java"],
    deps = [
         "//proto/task:task_java_proto",
        "//java/com/task:sample_tasks_util",
    ],
)

java_binary(
    name = "test_eval_server",
    srcs = ["TestEvalServer.java"],
    deps = [
    ":schedule_handler"
    ],
    main_class = "com.eval.TestEvalServer"
)