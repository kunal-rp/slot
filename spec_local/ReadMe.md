See AWS & CI readme for more info

will be pushing directly to minikube local cluster for testing 


1) start up minikube ; delete cluster if needed
2) run startup.sh 
- gateway img will be compiled sand push ed to cluster seperatly
3) expose minikube server for gateway, 'minikube service gateway -n slot' 