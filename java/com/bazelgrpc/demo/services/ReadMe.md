#Services

All microservice servers should reside here.

Running W/ One Container Instance Locally :

1. bazel run //java/com/bazelgrpc/demo/services:....
   - docker image should be created 'docker images'
2. docker run -p 127.0.0.1:LOCAL_PORT:EXTERNAL_PORT (both ports are usually the same) CONTAINER_ID

---

Pushing the Container Image to ECR:

1. ensure that you are logged in

- aws configure
- authenticate registry locally : https://docs.aws.amazon.com/AmazonECR/latest/userguide/getting-started-cli.html ( only have to run get-login-password command )

2. build target w/ container push will push to repo specified w/ run command

---

Preporcessing for Service Targets:
https://towardsdatascience.com/kubernetes-application-deployment-with-aws-eks-and-ecr-4600e11b2d3c

create a script that is added to the tool/cmd targets for all services targets:

1. checks if the service is modified from the git diff + cross reference to bazel query
2. parses service_versioning file that should detail out new service version tag/description  
   -checks that iff the given target is modified, there is an entry for the service and there is a new version tag
   -will have to git diff on service_versioning file and parse (https://stackoverflow.com/questions/21755720/git-diff-parse-in-shell-script)
