load("@rules_java//java:defs.bzl", "java_library")

package(default_visibility = ["//visibility:public"])

# The java_binary rule, like many others, has multiple outputs. We'll
# see that in action shortly.

java_library(
    name = "setup-util",
    srcs = ["SetupUtil.java"],
    deps = [
        "@maven//:com_google_guava_guava",
    ],
)

java_library(
    name = "service_module",
    srcs = ["ServiceModule.java"],
    deps = [
    	"@maven2//:com_google_inject_guice",
        "@maven2//:com_google_guava_guava",
        "//java/com/task/db:task_db_handler",
        "//java/com/task/db:main_task_db_handler"
    ],
)

java_library(
    name = "fake_service_module",
    srcs = ["FakeServiceModule.java"],
    deps = [
        "@maven2//:com_google_inject_guice",
        "@maven2//:com_google_guava_guava",
        "//java/com/task/db:task_db_handler",
        "//java/com/task/db:fake_task_db_handler"
    ],
)
