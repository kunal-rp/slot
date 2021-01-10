load("//:java/test/java_testing_generator.bzl", "java_unit_test")

package(default_visibility = ["//visibility:public"])

java_unit_test(
    name = "generative_schedule_util_test",
    pkg="com.eval",
    srcs = ["GenerativeScheduleUtilTest.java"],
    deps = [
        "//proto/task:task_java_proto",
        "//java/com/task:sample_tasks_util",
        "//java/com/eval:generative_schedule_util"
    ],

)
