Deployment to AWS Specific
(https://towardsdatascience.com/kubernetes-application-deployment-with-aws-eks-and-ecr-4600e11b2d3c)

Ensure the awsCLI is logged into ecr

- aws ecr get-login-password --region REGION | docker login --username AWS --password-stdin ECR_URI

Here are the general steps for creating/setup for k8s cluster:

1.  Create Virtal Private Cloud
    -specify public/private subnets(IP's in VPC) for cluster
2.  Create K8s Cluster on said cloud
    -note that two services are needed; one to expose the poll service internally, and another to expose the action externally
3.  Be sure to expose the port for the public workers that matches nodeport exposeds
    - should only need to modify the public subnet inboud rules & add the new port

Running specific pods on specific nodes:
So we are running

--
Pushing new images for CI/CD

Developer would create a new branch & make a change, ci hookup will run tests and ensure that everything passes and works. On merge to main branch, images should get integrated into cluster.

There are two general optinos for this : 
1) Find what images are new , push to registry
- follow up change to update the deployment yaml
2) Directly update deployment upon change
- use k8s rules defined, specifying the cluster and image target 
- no need to push to registry
- rollback would = rollback of change, which would be easy to keep track of and perform 

Option #1 has a ci folder w/ scripts that can be used for this, along w/ .circle ci yaml  
Option #2 has a ci folder that k8s build rules in spec_aws + kubeconfig to allow for testing

Performing Option #2: 
- create vpc and cluster w/ eks 
- ensure that everything is working, test the api
- make change to api/service
- bazel run //spec_aws:k8s.apply
