load("@io_bazel_rules_k8s//k8s:object.bzl", "k8s_object")
load("@io_bazel_rules_k8s//k8s:objects.bzl", "k8s_objects")

k8s_object(
  name = "deployment_gateway",
  kind = "deployment",
  kubeconfig = ":config",
  cluster = "minikube",
  template = ":deployment_gateway.yaml",
  images = {
      "krpdkr/slot_gateway": "//nodejs:nodejs_gateway_image"
  },
)

k8s_object(
  name = "deployment_template",
  kind = "deployment",
  kubeconfig = ":config",
  cluster = "minikube",
  template = ":deployment_template.yaml",
  images = {
      "krpdkr/slot_template": "//java/com/template:template_java_image"
  },
)

k8s_object(
  name = "service_gateway",
  template = ":service_gateway.yaml",
  kubeconfig = ":config",
  cluster = "minikube",
  kind = "service"
)

k8s_object(
  name = "service_template",
  template = ":service_template.yaml",
  kubeconfig = ":config",
  cluster = "minikube",
  kind = "service"
)

k8s_object(
  name = "ns_slot",
  template = ":ns_slot.yaml",
  kubeconfig = ":config",
  cluster = "minikube",
  kind = "namespace"
)

k8s_objects(
    name = "deployment",
    objects = [
      ":deployment_gateway",
      ":deployment_template",
    ]
)

k8s_objects(
    name = "services",
    objects = [
      ":service_gateway",
      ":service_template",
    ]
)

k8s_objects(
    name = "k8s",
    objects = [
    ":ns_slot",
      ":deployment_template",
      ":services",
    ]
)