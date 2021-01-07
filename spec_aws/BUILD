load("@io_bazel_rules_k8s//k8s:object.bzl", "k8s_object")
load("@io_bazel_rules_k8s//k8s:objects.bzl", "k8s_objects")

k8s_object(
  name = "deployment_action",
  kind = "deployment",
  kubeconfig = ":config",
  cluster = "arn:aws:eks:us-east-2:534378753229:cluster/bg-7-cluster",
  template = ":deployment_action.yaml",
  images = {
      "534378753229.dkr.ecr.us-east-2.amazonaws.com/action-bazeldemo:dev": "//java/com/bazelgrpc/demo/services:action_image"
  },
)

k8s_object(
  name = "deployment_gateway",
  kind = "deployment",
  kubeconfig = ":config",
  cluster = "arn:aws:eks:us-east-2:534378753229:cluster/bg-7-cluster",
  template = ":deployment_gateway.yaml",
  images = {
      "534378753229.dkr.ecr.us-east-2.amazonaws.com/gateway-bazeldemo:dev": "//nodejs:gateway_image"
  },
)

k8s_object(
  name = "deployment_poll",
  kind = "deployment",
  kubeconfig = ":config",
  cluster = "arn:aws:eks:us-east-2:534378753229:cluster/bg-7-cluster",
  template = ":deployment_poll.yaml",
  images = {
      "534378753229.dkr.ecr.us-east-2.amazonaws.com/poll-bazeldemo:dev": "//java/com/bazelgrpc/demo/services:poll_image"
  },
)

k8s_object(
  name = "service_action",
  template = ":service_action.yaml",
  kubeconfig = ":config",
  cluster = "arn:aws:eks:us-east-2:534378753229:cluster/bg-7-cluster",
  kind = "service"
)

k8s_object(
  name = "service_gateway",
  template = ":service_gateway.yaml",
  kubeconfig = ":config",
  cluster = "arn:aws:eks:us-east-2:534378753229:cluster/bg-7-cluster",
  kind = "service"
)

k8s_object(
  name = "service_poll",
  template = ":service_poll.yaml",
  kubeconfig = ":config",
  cluster = "arn:aws:eks:us-east-2:534378753229:cluster/bg-7-cluster",
  kind = "service"
)

k8s_object(
  name = "ns_bazelgrpc",
  template = ":ns_bazelgrpc.yaml",
  kubeconfig = ":config",
  cluster = "arn:aws:eks:us-east-2:534378753229:cluster/bg-7-cluster",
  kind = "namespace"
)

k8s_objects(
    name = "deployment",
    objects = [
      ":deployment_action",
      ":deployment_gateway",
      ":deployment_poll",
    ]
)

k8s_objects(
    name = "services",
    objects = [
      ":service_action",
      ":service_gateway",
      ":service_poll",
    ]
)

k8s_objects(
    name = "k8s",
    objects = [
      ":deployment",
      ":services",
      ":ns_bazelgrpc",
    ]
)