load("@io_bazel_rules_k8s//k8s:object.bzl", "k8s_object")
load("@io_bazel_rules_k8s//k8s:objects.bzl", "k8s_objects")

k8s_object(
  name = "deployment_gateway",
  kind = "deployment",
  kubeconfig = ":config",
  cluster = "arn:aws:eks:us-east-2:534378753229:cluster/bg-7-cluster",
  template = ":deployment_gateway.yaml",
  images = {
      "gateway_image": "//nodejs:gateway_image"
  },
)

k8s_object(
  name = "service_gateway",
  template = ":service_gateway.yaml",
  kubeconfig = ":config",
  cluster = "arn:aws:eks:us-east-2:534378753229:cluster/bg-7-cluster",
  kind = "service"
)

k8s_object(
  name = "ns_slot",
  template = ":ns_slot.yaml",
  kubeconfig = ":config",
  cluster = "arn:aws:eks:us-east-2:534378753229:cluster/bg-7-cluster",
  kind = "namespace"
)

k8s_objects(
    name = "deployment",
    objects = [
      ":deployment_gateway",
    ]
)

k8s_objects(
    name = "services",
    objects = [
      ":service_gateway",
    ]
)

k8s_objects(
    name = "k8s",
    objects = [
      ":deployment",
      ":services",
      ":ns_slot",
    ]
)