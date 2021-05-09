workspace(name = "demo_proto_and_java" ,
    # Map the @npm bazel workspace to the node_modules directory.
    # This lets Bazel use the same node_modules as other local tooling.
    managed_directories = {
        "@nodejs_modules": ["nodejs/node_modules"],
        "@frapp_modules": ["frontend/app/node_modules"]
        })

# functions to get external libs
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# Bazel Basic java - jdk and toolchain 
http_archive(
    name = "rules_java",
    sha256 = "220b87d8cfabd22d1c6d8e3cdb4249abd4c93dcc152e0667db061fb1b957ee68",
    url = "https://github.com/bazelbuild/rules_java/releases/download/0.1.1/rules_java-0.1.1.tar.gz",
)

load("@rules_java//java:repositories.bzl", "rules_java_dependencies", "rules_java_toolchains")
rules_java_dependencies()
rules_java_toolchains()


# proto - grpc service ( should also get  proto_rules dep )
http_archive(
    name = "rules_proto_grpc",
    urls = ["https://github.com/rules-proto-grpc/rules_proto_grpc/archive/2.0.0.tar.gz"],
    sha256 = "d771584bbff98698e7cb3cb31c132ee206a972569f4dc8b65acbdd934d156b33",
    strip_prefix = "rules_proto_grpc-2.0.0",
)
load("@rules_proto_grpc//:repositories.bzl", "rules_proto_grpc_toolchains", "rules_proto_grpc_repos")
rules_proto_grpc_toolchains()
rules_proto_grpc_repos()

load("@rules_proto_grpc//java:repositories.bzl", rules_proto_grpc_java_repos="java_repos")
rules_proto_grpc_java_repos()

load("@io_grpc_grpc_java//:repositories.bzl", "IO_GRPC_GRPC_JAVA_ARTIFACTS", "IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS", "grpc_java_repositories")

grpc_java_repositories()

# maven_install
RULES_JVM_EXTERNAL_TAG = "3.3"
RULES_JVM_EXTERNAL_SHA = "d85951a92c0908c80bd8551002d66cb23c3434409c814179c0ff026b53544dab"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

#guice
#grpc testing
grpc_version = "1.22.1"
guice_version = "4.1.0"
maven_install(
    name = "maven",
    artifacts = [
        "io.grpc:grpc-testing:%s" % grpc_version,
        "com.google.guava:guava:27.0-jre",
        'com.google.inject:guice:' + guice_version,
    ],
    repositories = [
        "https://jcenter.bintray.com/",
        "https://repo1.maven.org/maven2",
        "https://maven.google.com",
    ],
)

## rules_docker
http_archive(
    name = "io_bazel_rules_docker",
    sha256 = "1698624e878b0607052ae6131aa216d45ebb63871ec497f26c67455b34119c80",
    strip_prefix = "rules_docker-0.15.0",
    urls = ["https://github.com/bazelbuild/rules_docker/releases/download/v0.15.0/rules_docker-v0.15.0.tar.gz"],
)
load(
    "@io_bazel_rules_docker//repositories:repositories.bzl",
    container_repositories = "repositories",
)
container_repositories()
load("@io_bazel_rules_docker//repositories:deps.bzl", container_deps = "deps")
container_deps()

# Load java_image rules to create java docker images to run grpc services
load(
    "@io_bazel_rules_docker//java:image.bzl",
    _java_image_repos = "repositories",
)
_java_image_repos()

# Load the macro that allows you to customize the docker toolchain configuration.
load("@io_bazel_rules_docker//toolchains/docker:toolchain.bzl",
    docker_toolchain_configure="toolchain_configure"
)

docker_toolchain_configure(
  name = "docker_config",
# abosolute path to credentials directory 
  client_config="${ROOT}/credentials",
  docker_flags = [
    "--tls",
    "--log-level=error",
  ],
)


#nodejs - already installed from proto grpc 
load("@rules_proto_grpc//nodejs:repositories.bzl", rules_proto_grpc_nodejs_repos="nodejs_repos")
rules_proto_grpc_nodejs_repos()

# The npm_install rule runs yarn anytime the package.json or package-lock.json file changes.
# It also extracts any Bazel rules distributed in an npm package.
load("@build_bazel_rules_nodejs//:index.bzl", "npm_install","yarn_install")
npm_install(
    # Name this npm so that Bazel Label references look like @npm//package
    name = "nodejs_modules",
    package_json = "//nodejs:package.json",
    package_lock_json = "//nodejs:package-lock.json",
)

yarn_install(
    name = "frapp_modules",
    package_json = "//frontend/app:package.json",
    yarn_lock = "//frontend/app:yarn.lock"
)

# Load nodejs_image rules to create java docker images to run grpc services
load(
    "@io_bazel_rules_docker//nodejs:image.bzl",
    _nodejs_image_repos = "repositories",
)
_nodejs_image_repos()

# k8s - push images directly to cluster 
http_archive(
    name = "io_bazel_rules_k8s",
    strip_prefix = "rules_k8s-0.6",
    urls = ["https://github.com/bazelbuild/rules_k8s/archive/v0.6.tar.gz"],
    sha256 = "51f0977294699cd547e139ceff2396c32588575588678d2054da167691a227ef",
)

load("@io_bazel_rules_k8s//k8s:k8s.bzl", "k8s_repositories")

k8s_repositories()

load("@io_bazel_rules_k8s//k8s:k8s_go_deps.bzl", k8s_go_deps = "deps")

k8s_go_deps()
