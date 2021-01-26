load("@rules_java//java:defs.bzl", "java_library")
load("@io_bazel_rules_docker//java:image.bzl", "java_image")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "sample_tasks_util",
    srcs = ["SampleTasksUtil.java"],
    deps = [
        "//proto/task:task_java_proto",
    ],
)

java_library(
    name = "task_handler",
    srcs = ["TaskHandler.java"],
    deps = [
       "//proto/task:task_service_java_proto",
    ],
)


java_library(
    name = "main_task_handler",
    srcs = ["MainTaskHandler.java"],
    deps = [
        ":task_handler",
        "//proto/task:task_service_java_proto",
        "//java/com/util:setup-util",
    ],
)


java_library(
    name = "fake_task_handler",
    srcs = ["FakeTaskHandler.java"],
    deps = [
        ":task_handler",
        "//proto/task:task_service_java_proto",
    ],
)


java_image(
    name = "task_java_image",
     srcs = ["TaskService.java"],
     deps = [
        "@maven//:com_google_inject_guice",
     	"@io_grpc_grpc_java//api",
        ":sample_tasks_util",
        ":task_handler",
        "//proto/task:task_service_java_proto",
        "//java/com/util:service_module",
        "//java/com/util:setup-util",
        "//java/com/task/db:task_db_handler",
        "//java/com/task/template:generative_schedule_util",
     ],
    main_class = "com.task.TaskService",
)