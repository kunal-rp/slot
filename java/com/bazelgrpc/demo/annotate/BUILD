load("@rules_java//java:defs.bzl","java_library")

package(default_visibility = ["//visibility:public"])

# The java_binary rule, like many others, has multiple outputs. We'll
# see that in action shortly.

java_library(
    name = "annotate-post-util",
    srcs = ["AnnotatePostUtil.java"],
    deps = [
        "//proto/annotate:annotate_java_proto",
    ],
)

java_library(
    name = "annotate-service-impl",
    srcs = ["AnnotateManagementImpl.java"],
    deps = [
        ":annotate-post-util",
        "//proto/annotate:annotate_service_java_proto",
    ],
)

java_binary(
    name = "annotate-client",
    srcs = ["AnnotatePostClient.java"],
    deps = [
        "//proto/annotate:annotate_service_java_proto",
        "//java/com/bazelgrpc/demo/util:setup-util"
    ],
)