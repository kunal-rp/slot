load("@rules_java//java:defs.bzl","java_library")

package(default_visibility = ["//visibility:public"])


java_library(
    name = "generative_schedule_util",
    srcs = ["GenerativeScheduleUtil.java"],
    deps = [
         "//proto/task:task_java_proto",
        "//java/com/task:sample_tasks_util",
    ],
)

java_binary(
    name = "test_eval_server",
    srcs = ["TestEvalServer.java"],
    deps = [
        ":generative_schedule_util",
        "//proto/task:task_java_proto",
        "//java/com/task:sample_tasks_util",
    ],
    main_class = "com.eval.TestEvalServer"
)