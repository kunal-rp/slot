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

java_library(
    name = "template_service_impl",
    srcs = ["TemplateServiceImpl.java"],
    deps = [
        ":generative_schedule_util",
        "//proto/template:template_service_java_proto",
        "//java/com/task:sample_tasks_util"
    ],
)

java_library(
    name = "template_handler",
    srcs = ["TemplateHandler.java"],
    deps = [
       "//proto/template:template_service_java_proto",
    ],
)

java_library(
    name = "main_template_handler",
    srcs = ["MainTemplateHandler.java"],
    deps = [
       "//proto/template:template_service_java_proto",
       "//java/com/bazelgrpc/demo/util:setup-util",
    ],
)


java_library(
    name = "fake_template_handler",
    srcs = ["FakeTemplateHandler.java"],
    deps = [
       "//proto/template:template_service_java_proto",
    ],
)

java_binary(
    name = "test_template_handler",
    srcs = ["TestTemplateHandler.java"],
    deps = [
        ":main_template_handler",
        "//proto/template:template_service_java_proto",
    ],
    main_class = "com.template.TestTemplateHandler"
)




java_image(
    name = "template_java_image",
     srcs = ["TemplateService.java"],
     deps = [
        ":template_service_impl",
        "//java/com/bazelgrpc/demo/util:setup-util",
        "@io_grpc_grpc_java//api",
     ],
    main_class = "com.template.TemplateService",
)

# create container image
container_image(
    name = "template_image",
    base = "template_java_image",
    creation_time = "{BUILD_TIMESTAMP}",
    stamp = True,
)