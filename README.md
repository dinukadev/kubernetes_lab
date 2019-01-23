## Goal
The goal of this lab is to get you familiarised with Kubernetes and deploy the sample project provided into a Google Kubernetes Engine cluster.

## Pre-requisites

- Docker
- Java 8
- Maven 3+
- Signup for a Google cloud account

## Overview of Kubernetes
 Kubernetes provides a uniform way of automating, managing and scaling the deployment of your containerized applications.

## Steps

### Setting up the gcloud utility

First off we need to install Google cloud utils connecting to the Google cloud account you created as part of the pre-requisites.

Follow the steps mentioned [here](https://cloud.google.com/sdk/docs/downloads-interactive)

Next up, we need to install the kubectl component which can be done using the following command;

```
gcloud components install kubectl
```

Once you have that setup, it is time to log in to the [Google cloud console](https://console.cloud.google.com)

### Creating the Google Kubernetes Engine(GKE) Cluster()

- First, lets create a new project. On the header, right next to the working `Google Cloud Platform` you will see a drop down. By default GCP creates a default proect for you. For this lab, let us create a new project by selecting `New project` and giving it the name `kube-lab`
- Go to Kubernetes->Clusters and then click on Create Clusters
- Name the cluster as `kube-lab-1`
- Select the Zone as `australia-southeast1-a`. On a production cluster you would typical create it Regional for high availability
- Set the number of nodes to `1`
- Keep everything else to default
- Click on Create which will then create the Cluster


Once the cluster is setup, we can query the cluster using the gcloud utility with the following commands;

- The following command will show you the virtual machines that were created when you created the Kubernetes cluster
```
gcloud compute instances list
```

- The following command will log into the Kubernetes Cluster

```
gcloud container clusters get-credentials kube-lab-1 --zone australia-southeast1-a --project kube-lab
```

_ After you are logged in, you can query the Kubernetes cluster. For now let us see how many nodes are available on the cluster(should be one since we selected one when creating the cluster)

```
kubectl get nodes
```

### Building the sample application

- Go into the excercises/excercise_1/justiceleague_management_system on the terminal
- Execute `mvn clean install` to build the project
- Next, it is time to build the docker image which will be used to deploy
- Issue the command `docker build --force-rm=true -t jlms .`
- Next we need to create a tag to push the docker image to our Google Container Registry(GCR)
- Issue the command `docker tag jlms:latest gcr.io/kube-lab/jlms:latest`
- We then push the tag created to GCR using the command `docker push gcr.io/kube-lab/jlms:latest`

Once we have done that, we can now deploy our containerised application to the GKE cluster we created before. For that we need to have a kubernetes deployment file defined which is a YAML file defining the resources being deployed.

For this lab, you can open up the kubernetes deployment plan found at `excercises/excercise_1/justiceleague_management_system/jlms-deployment.yaml`

Let us go into the content of the file at a high level;

```
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: ms-jlms
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: ms-jlms
    spec:
      containers:
      - name: ms-jlms
        image: gcr.io/kube-lab/jlms:latest
        imagePullPolicy: Always
        resources:
          requests:
            cpu: "50m"
        ports:
        - name: http
          containerPort: 8080
        env:
        - name: ENVIRONMENT
          value: "prod"
        - name: XMS
          value: "-Xms512m"
        - name: XMX
          value: "-Xmx512m"
---
kind: Service
apiVersion: v1
metadata:
  labels:
    app: ms-jlms
  name: ms-jlms
spec:
  type: ClusterIP
  ports:
  - name: "application"
    port: 80
    targetPort: 8080
  selector:
    app: ms-jlms
```

We have two resource defined here which is defined with the `kind` attribute.

One is a deployment and the other is a service.

A deployment defines the way in which your containerised application is deployed to the kubernetes cluster.

A service on the other hand is the way of exposing your application to the cluster so that other applications can make calls to your application.

On the deployment, you can see that we are pulling the image that we pushed using the docker command we used before. Environment variables can also be passed as part of the deployment so you can personalise your parameters for each environment. If you look at the `excercises/excercise_1/justiceleague_management_system/Dockerfile` you will see that the last line on that file consumes the environment variables passed on this kubernetes deployment file.

We will not go into detail on the Service as part of this lab.

### Deploying the sample application

- First of all, go into the directory `excercises/excercise_1/justiceleague_management_system` on your terminal
- Issue the following command;
```
gcloud container clusters get-credentials kube-lab-1 --zone australia-southeast1-a --project kube-lab
```
This command will log you into the cluster so that you can issue kubernetes commands on the clusters

- Next, issue the following command `kubectl create -f jlms-deployment.yaml`. This will start the deployment of your application to the kubernetes cluster
- To see the progress you can keep issuing the command `kubectl get pods`

Sample output;

```
NAME                       READY     STATUS              RESTARTS   AGE
ms-jlms-5b8fd76f64-5nphc   0/1       ContainerCreating   0          2s
```

- Wait until the `STATUS` changes to `Running`

Congratulations! You have deployed your very first application to a kubernetes cluster.

To see whether your server started up as expected, you can check the logs of your container by using the following command;

```
kubectl get logs <pod name>
```

The `<pod name>` is the name displayed when you did `kubectl get pods`. With the previous example, it would be `ms-jlms-5b8fd76f64-5nphc`

You should see the logs as ;

```
2019-01-23 01:34:24.422  INFO 1 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2019-01-23 01:34:24.441  INFO 1 --- [           main] c.j.j.JusticeLeagueManagementApplication : Started JusticeLeagueManagementApplication in 8.046 seconds (JVM running for 9.299)
```

You can get more information on your deployment such as which node it is deployed on for example and its internal IP by issusing the command `kubectl describe pod <pod name>`


Finally to clean everything up and to delete your deployment you can issue the command `kubectl delete -f jlms-deployment.yaml`

Do not forget to clean up your GKE cluster as well once you are done.
