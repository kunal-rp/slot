##Frontend App 

#Run Dev server
- ibazel run //frontend/app:dev_server

#Run Prod Server
- http local : ibazel run //frontend/app:dev_server
- docker image gen : bazel run //frontend/app:prod_image