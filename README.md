### Kubernetes

## Introduction
The following project is an pproach based example to discover the main characteristics of Kubernetes.



## What is Kubernetes
Kubernetes is an open source container orchestration tool developed by GOOGLE.
In other words, Kubernetes helps us to manage containereized application in different deployment enviroments such as
phisical machine, virtual machine or cloud.



## Why Kubernetes
Nowadays, the most common paradigm in software engineering is the microservice based project where every microservice is a containereized app.
In most cases the number of container in the project is huge (hundreds or thousands) and manage all them should be a real pain.
That's why Kubernets is born, to relieve that pain in managing containers lifecycle using centralized configurations.



## Kubernetes features
The main 3 features that Kubernetes offers are:

* High availability (no downtime)
* Scalability (increase or decrease the containers number)
* Disaster Recovery (backup and restore)



## Kubernetes Architecture
When you deploy Kubernetes, you get a cluster.
A Kubernetes cluster consists of a set of worker machines, called nodes, that run containerized applications. Every cluster has at least one worker node.
The worker node(s) host the Pods that are the components of the application workload. The control plane manages the worker nodes and the Pods in the cluster.
In production environments, the control plane usually runs across multiple computers and a cluster usually runs multiple nodes, providing fault-tolerance and high availability.

(screenshoot)

Let's explain the components depicted in the image.

### Control Plane Components 
The control plane's components make global decisions about the cluster (for example, scheduling), as well as detecting and responding to cluster events 
(for example, starting up a new pod when a Deployment's replicas field is unsatisfied).
Control plane components can be run on any machine in the cluster.

* kube-apiserver
The API server is a component of the Kubernetes control plane that exposes the Kubernetes API. The API server is the front end for the Kubernetes control plane.
The main implementation of a Kubernetes API server is kube-apiserver.
We can interact with apiserver using the UI, the API or the CLI.

* etcd 
Consistent and highly-available key value store used as Kubernetes' backing store for all cluster data.
If your Kubernetes cluster uses etcd as its backing store, make sure you have a back up plan for the data.
Basically, is the component that helds anytime the current state of the kubernetes cluster. So it has all the data
of each nodes, each conatiners etc.

* kube-scheduler 
Control plane component that watches for newly created Pods with no assigned node, and selects a node for them to run on.
Factors taken into account for scheduling decisions include: individual and collective resource requirements, 
hardware/software/policy constraints, affinity and anti-affinity specifications, data locality, inter-workload interference, and deadlines.

* kube-controller-manager
Mainly is the Control Plane Component used for keeps an overview of what is happening in the cluster whether something to be repair or restarted etc.


* cloud-controller-manager
A Kubernetes control plane component that embeds cloud-specific control logic. The cloud controller manager lets you link your cluster into your 
cloud provider's API, and separates out the components that interact with that cloud platform from components that only interact with your cluster.
The cloud-controller-manager only runs controllers that are specific to your cloud provider. If you are running Kubernetes on your own premises,
 or in a learning environment inside your own PC, the cluster does not have a cloud controller manager.

### Node Components
Node components run on every node, maintaining running pods and providing the Kubernetes runtime environment.

* kubelet
An agent that runs on each node in the cluster. It makes sure that containers are running in a Pod.
The kubelet takes a set of PodSpecs that are provided through various mechanisms and ensures that the containers described in those PodSpecs 
are running and healthy. The kubelet doesn't manage containers which were not created by Kubernetes.

* kube-proxy
Kube-Proxy is a Kubernetes agent installed on every node in the cluster. It monitors the changes that happen to Service objects and their endpoints. Then it translates these changes into actual network rules inside the node.
* Container runtime (CRI)
A fundamental component that empowers Kubernetes to run containers effectively. It is responsible for managing the execution and lifecycle of containers within the Kubernetes environment.



## Main Kubernetes Component (Terminology)
Let's see the main terminology used to describe the kubernetes components.

### Cluster
A Kubernetes cluster is comprised of nodes, which can be either VMs or physical servers. When you use Kubernetes, you are always managing a cluster. 
There must be at least one instance of the Kubernetes control plane running on a node, and at least one node for pods to execute on. 
Typically, the cluster will have multiple nodes to handle the scaling of applications as workloads change, whether due to time of day, seasonality, or other reason.

### Node and Pod
Nodes are the physical servers or VMs that comprise a Kubernetes Cluster.
A Kubernetes pod is a collection of one or more containers, and is the smallest unit of a Kubernetes application. Any given pod can be composed of multiple, 
tightly coupled containers (an advanced use case) or just a single container (a more common use case). Containers are grouped into Kubernetes pods in order 
to increase the intelligence of resource sharing. The relationship of pods to clusters is why Kubernetes does not run containers directly, instead running 
pods to ensure that each container within them shares the same resources and local network. Grouping containers in this way allows them to communicate 
between each other as if they shared the same physical hardware, while still remaining isolated to some degree.
This organization of containers into pods is the basis for one of Kubernetes’ well-known features: replication. When containers are organized into pods, 
Kubernetes can use replication controllers to horizontally scale an application as needed. In effect, this means that if a single pod becomes overloaded, 
Kubernetes can automatically replicate it and deploy it to the cluster. In addition to supporting healthy functioning during periods of heavy load, 
pods are also often replicated continuously to provide failure resistance to the system.
Strictly speaking, pods are an abstraction layer over the container to group container that must share the same hardware.

Note!
1) To comunicate over the network each Pod must have its own IP Address.

2) One main characteristic of the Kubernetes Pods is that they are Ephemeral that means they can "die" very easily. When that happen a new Pod will be created 
with a brand new IP address. This can lead to the inconvenient that you have to specify to all the other Pods that comunicate with the Pod that just died the
new IP address! To overcome this problem we can use the Service component.

### Service
The Service API, part of Kubernetes, is an abstraction to help you expose groups of Pods over a network. Each Service object defines a logical set of endpoints 
(usually these endpoints are Pods) along with a policy about how to make those pods accessible.
For example, consider a stateless image-processing backend which is running with 3 replicas. Those replicas are fungible—frontends do not care which backend they use. 
While the actual Pods that compose the backend set may change, the frontend clients should not need to be aware of that, nor should they need to keep track of the set of backends themselves.
The Service abstraction enables this decoupling.
We can see the service as a static or permanent IP address that can be attached to each Pod.

Note!

1) As we have already said, remember that Service and Pod lifecycle are NOT CONNECTED that means in two words: if Pod crash the service stays!

### Ingress
But what if we want to expose our application to be accessible from external user by a web browser?
We must use Ingress. Ingress exposes HTTP and HTTPS routes from outside the cluster to services within the cluster. 
Traffic routing is controlled by rules defined on the Ingress resource.

(attach ingress screen)

### ConfigMap (cm)
A ConfigMap is an API object used to store non-confidential data in key-value pairs. Pods can consume ConfigMaps as environment variables, command-line arguments, or as configuration files in a volume.
A ConfigMap allows you to decouple environment-specific configuration from your container images, so that your applications are easily portable.


### Secrets
A Secret is an object that contains a small amount of sensitive data such as a password, a token, or a key. Such information might otherwise be put in a Pod specification or in a container image. 
Using a Secret means that you don't need to include confidential data in your application code.
Because Secrets can be created independently of the Pods that use them, there is less risk of the Secret (and its data) being exposed during the workflow of creating, viewing, and editing Pods. 
Kubernetes, and applications that run in your cluster, can also take additional precautions with Secrets, such as avoiding writing sensitive data to nonvolatile storage.

Caution:
Kubernetes Secrets are, by default, stored unencrypted in the API server's underlying data store (etcd). Anyone with API access can retrieve or modify a Secret, and so 
can anyone with access to etcd. Additionally, anyone who is authorized to create a Pod in a namespace can use that access to read any Secret in that namespace; this 
includes indirect access such as the ability to create a Deployment.  
In order to safely use Secrets, take at least the following steps:

1) Enable Encryption at Rest for Secrets.
2) Enable or configure RBAC rules with least-privilege access to Secrets.
3) Restrict Secret access to specific containers.
4) Consider using external Secret store providers.

Note!
1) Secrets are similar to ConfigMaps but are specifically intended to hold confidential data.


### Volume
The Volume component is a data storage not connected to the with the Pod lifecycle.
The Volume come into play when we talk about dealing with failure of containereized database in Pod and the consequent loss of data. Let me explain with an short example:
Let's suppose we have a containereized database in our node where our app store all the information. What could it happen to the data if the pod with the db crashes? short answer, we lose our data
cause, during a crash, kubelet restarts the database container with a clean state.
Precisely to avoid this kind of scenario there is the Volume component in Kubernetes.  

Note!
1) Another problem that Kubernetes' Volume solve is when multiple containers are running in a Pod and need to share files. It can be challenging to setup and access a shared filesystem across all 
of the containers but with Volume is much easier.


### Deployment & StatefulSet
A Kubernetes Deployment is an abstraction of Pods that allows us to replicate Pods that runs stateless (= not persist data) application.
Deeply, a deployment is a component that provides declarative updates to applications. A deployment allows you to describe an application’s life cycle, such as which 
images to use for the app, the number of pods there should be, and the way in which they should be updated. 
After an object has been created, the cluster works to ensure that the object exists, maintaining the desired state of your Kubernetes cluster. 
The process of manually updating containerized applications can be time consuming and tedious. Upgrading a service to the next version requires starting the new version of 
the pod, stopping the old version of a pod, waiting and verifying that the new version has launched successfully, and sometimes rolling it all back to a previous version in the case of failure.
Performing these steps manually can lead to human errors, and scripting properly can require a significant amount of effort, both of which can turn the release process into a bottleneck. 
A Kubernetes deployment makes this process automated and repeatable. Deployments are entirely managed by the Kubernetes backend, and the whole update process is performed on 
the server side without client interaction.  
A deployment ensures the desired number of pods are running and available at all times. The update process is also wholly recorded, and versioned with options to pause, 
continue, and roll back to previous versions.  


A Kubernetes StatefulSet is an abstraction of Pods that allows us to replicate Pods that runs stateful (= persist data (such as database)) application.
The replication of stateful applications cannot be done using deployment cause database has state which is its data! Meaning that if we have clones or replicas of a 
database they would all need to access the same shared data storage and there we would need some kind of mechanism that manages which pods are currently writing to the storage or
which pods are reading from the storage in order to avoid data inconsistencies! That mechanism in addition to replicating feature is offered by StatefulSet component that is meant specifically
for application like databases.


Note!
1) Replicate database using StatefulSet in kubernetes cluster can be somewhat tedious and more challenging than replicate stateless app, that's why, it is a common practice to host database 
application outside of the kubernetes cluster and have just stateless application that run and replicate inside the kubernetes cluster and communicate with external database.



## Practical Project
The project you find in this github repo is a practical approach to the basic concepts of kubernetes. The project have 2 principal elements: a Quarkus app that expose CRUD operations and a
Postgresql database. These two components were deployed in a single Kubernetes cluster (minikube) and were used service, config secrete and config map Kubernetes components.
To run locally this project you have to install docker on your machine. After that you have to download the kubernetes arc folder and run the following commands:

1) minikube start --driver docker
2) kubectl apply -f (one at time the file in the kubernetes arc folder, starting from config, secret and then postgres and app)
3) create a tunnel towards the app service
