bazel run //spec_local:k8s.apply
bazel run //spec_local:deployment_gateway.apply --platforms=@build_bazel_rules_nodejs//toolchains/node:linux_amd64