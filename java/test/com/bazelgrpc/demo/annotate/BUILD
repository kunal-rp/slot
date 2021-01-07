load("//:java/test/java_testing_generator.bzl", "java_unit_test")

package(default_visibility = ["//visibility:public"])

java_unit_test(
    name = "annotate_unit_test",
    pkg="com.bazelgrpc.demo.annotate",
    srcs = glob(["*.java"]),
    deps = [
        "//proto/annotate:annotate_service_java_proto",
        "//proto/annotate:annotate_java_proto",
    	"//java/com/bazelgrpc/demo/annotate:annotate-post-util",
    	"//java/com/bazelgrpc/demo/annotate:annotate-service-impl",
    	"@io_grpc_grpc_java//api",
        "@io_grpc_grpc_java//core:inprocess",
        "@maven//:io_grpc_grpc_testing",
    ],

)
