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

java_library(
    name = "task_service_impl",
    srcs = ["TaskServiceImpl.java"],
    deps = [
        "@maven//:com_google_inject_guice",
        "@io_grpc_grpc_java//api",
        "@com_google_api_grpc_proto_google_common_protos//:com_google_api_grpc_proto_google_common_protos",
        ":task_handler",
        "//proto/task:task_service_java_proto",
        "//proto/task:task_db_java_proto",
        "//java/com/task/db:task_db_handler",
        "//java/com/task/template:generative_schedule_util",
    ],
)


java_image(
    name = "task_java_image",
     srcs = ["TaskService.java"],
     deps = [
        "@maven//:com_google_inject_guice",
        "@io_grpc_grpc_java//api",
        ":task_service_impl",
        ":sample_tasks_util",
        "//java/com/util:service_module",
        "//java/com/util:setup-util",
     ],
    main_class = "com.task.TaskService",
)