load("//:java/test/java_testing_generator.bzl", "java_unit_test")

package(default_visibility = ["//visibility:public"])

java_unit_test(
    name = "generative_schedule_util_test",
    pkg="com.task",
    srcs = glob(["*.java"]),
    deps = [
    	"@io_grpc_grpc_java//api",
        "@io_grpc_grpc_java//core:inprocess",
        "@io_grpc_grpc_java//stub",
        "@maven//:io_grpc_grpc_testing",
        "@maven//:com_google_inject_guice",
        "//proto/task:task_service_java_proto",
        "//proto/task:task_java_proto",
        "//java/com/task:sample_tasks_util",
        "//java/com/task/template:generative_schedule_util",
        "//java/com/task:task_service_impl",
        "//java/com/util:fake_service_module",
        "//java/com/task/db:fake_task_db_handler"
    ],

)
