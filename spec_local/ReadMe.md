The best way to test all of this will be to run minikube node locally and apply the deployments after building services locally.

1. Start minikube
   -minikube start
2. Create new images locally
3. Clear old/Start new deployments and services
   -kubecbl apply -f local/

After editing the new images:

1. Link minikube docker dameon w/ local one 'eval $(minikube docker-env)'
   -will only work for that terminal , so create a new one and perform all steps
2. Run and create the new images locally, verify w/ 'docker images'
3. set image as REPO+TAG
