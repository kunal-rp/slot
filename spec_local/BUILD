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
  name = "deployment_task",
  kind = "deployment",
  kubeconfig = ":config",
  cluster = "minikube",
  template = ":deployment_task.yaml",
  images = {
      "krpdkr/slot_task": "//java/com/task:task_java_image"
  },
)

k8s_object(
  name = "deployment_frapp",
  kind = "deployment",
  kubeconfig = ":config",
  cluster = "minikube",
  template = ":deployment_frapp.yaml",
  images = {
      "krpdkr/slot_frapp": "//frontend/app:prod_image"
  },
)

k8s_object(
  name = "controller_linkerd",
  template = ":linkerd_controller.yaml",
  kubeconfig = ":config",
  cluster = "minikube",
  kind = "deployment"
)

k8s_object(
  name = "service_gateway",
  template = ":service_gateway.yaml",
  kubeconfig = ":config",
  cluster = "minikube",
  kind = "service"
)

k8s_object(
  name = "service_task",
  template = ":service_task.yaml",
  kubeconfig = ":config",
  cluster = "minikube",
  kind = "service"
)

k8s_object(
  name = "service_frapp",
  template = ":service_frapp.yaml",
  kubeconfig = ":config",
  cluster = "minikube",
  kind = "service"
)

k8s_object(
  name = "ingress",
  template = ":ingress.yaml",
  kubeconfig = ":config",
  cluster = "minikube",
  kind = "ingress"
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
      ":deployment_task",
      ":deployment_frapp",
    ]
)

k8s_objects(
    name = "services",
    objects = [
      ":service_gateway",
      ":service_task",
      ":service_frapp",
    ]
)

k8s_objects(
    name = "k8s",
    objects = [
        ":ns_slot",
        ":deployment_task",
        ":deployment_frapp",
        ":services",
        ":ingress"
    ]
)

k8s_objects(
    name = "controllers",
    objects = [
        ":controller_linkerd"
    ]
)