# Microservice Deployment on Kubernetes 
This document provides a guide for deploying a Spring Boot microservice application on a Kubernetes cluster. The provided YAML files configure a complete stack, including a MySQL database with persistent storage, the Spring Boot application, and ingress to expose the service.

### Project Description
This is a Spring Boot application designed as a simple microservice. The application is a backend service that likely handles basic CRUD operations for employees and departments, as suggested by the database schema in the db-init-configmap.yaml.

### Project and Tech Stack Details
Here are the key details from the pom.xml file, outlining the project's setup and dependencies:

**Project Name:** *Simple Microservice*

**Description:** *Simple Microservice project for Spring Boot*

**Java Version:** *17*

**Spring Boot Version:** *3.5.3*

**Key Dependencies:**

* **spring-boot-starter-data-jpa:** *For database persistence with JPA.*
* **spring-boot-starter-validation:** *For Jakarta validation.*
* **spring-boot-starter-web:** *For building web, including RESTful, applications.*
* **mysql-connector-j:** *MySQL JDBC driver for database connectivity.*
* **lombok:** *A library to reduce boilerplate code.*
* **springdoc-openapi-starter-webmvc-ui:** *To generate and serve Swagger UI for API documentation.*
### Code Repository
The source code for this microservice can be found on GitHub:
[https://github.com/getabhay/simple-microservice](https://github.com/getabhay/simple-microservice)

### Docker Hub
The Docker image for the application is available on Docker Hub:
[https://hub.docker.com/r/getabhay/simple-microservice](https://hub.docker.com/r/getabhay/simple-microservice)

### Service API Tier
The API documentation for the Spring Boot service is accessible via Swagger UI. Once the application is deployed and exposed through the ingress, you can access the API documentation at a URL similar to this (the exact URL will depend on your ingress configuration and domain):
[http://<your-ingress-ip-address>/swagger-ui/index.html](http://<your-ingress-ip-address>/swagger-ui/index.html)

## Commands to Create Resources
Here is the recommended order of operations, along with the commands to verify the status of each resource after it's created.

### Step 1: Create the Namespace
It is best practice to create a dedicated namespace first to logically group all your application's resources.

Command:

`kubectl apply -f namespace.yaml`

### Verification:
After running the command, use kubectl get to confirm the namespace exists:

`kubectl get namespaces`

You should see nagp-assignment-pg in the list of namespaces.

### Step 2: Create Persistent Storage for the Database
The persistent volume and persistent volume claim must be created before the database deployment. The PVC will wait for a matching PV to bind to before it can be used.

Commands:

`kubectl apply -f db-pv.yaml`

`kubectl apply -f db-pvc.yaml`

### Verification:
Check the status of the PersistentVolumeClaim (PVC). Its status should eventually change to Bound.

`kubectl get pvc -n nagp-assignment-pg`

If the status is Pending, you can use the describe command to see why it hasn't bound yet:

`kubectl describe pvc mysql-pvc -n nagp-assignment-pg`

### Step 3: Create Configuration and Secret Resources
These resources contain the non-sensitive configuration and sensitive credentials that your database and application will use. The db-statefulset and deployment files refer to these, so they must be created first.

Commands:

`kubectl apply -f db-secret.yaml`

`kubectl apply -f db-configmap.yaml`

`kubectl apply -f db-init-configmap.yaml`

### Verification:
Confirm that the ConfigMap and Secret resources were created:

`kubectl get configmaps -n nagp-assignment-pg`

`kubectl get secrets -n nagp-assignment-pg`

You should see db-config, db-secret, and db-init-config listed.

**Important Note:** *This step assumes you have already updated your db-init-configmap.yaml file to use BINARY(16) instead of UUID to avoid the MySQL syntax error.*

### Step 4: Deploy the Database
Next, deploy the MySQL database. The db-statefulset and db-service should be applied together.

Commands:

`kubectl apply -f db-service.yaml`

`kubectl apply -f db-statefulset.yaml`

### Verification:
Monitor the status of the database pods. It may take some time for the pods to become ready as they need to download the image, create the persistent volume, and run the initialization script.

`kubectl get pods -n nagp-assignment-pg`

The pod should eventually show 1/1 in the READY column. If the pod is not running or shows errors, check the logs and events:

`kubectl describe pod <pod-name> -n nagp-assignment-pg`

`kubectl logs <pod-name> -n nagp-assignment-pg`

### Step 5: Deploy the Spring Boot Application and Ingress
Finally, deploy your application and the ingress to expose it to external traffic. The application deployment and service depend on the database being available, and the ingress depends on the application's service.

Commands:

`kubectl apply -f backend-config.yaml`

`kubectl apply -f deployment.yaml`

`kubectl apply -f service.yaml`

`kubectl apply -f ingress.yaml`

### Verification:
Check the status of your application's deployment, pods, and ingress.

`kubectl get deployments -n nagp-assignment-pg`

`kubectl get pods -n nagp-assignment-pg`

`kubectl get ingress -n nagp-assignment-pg`

All application pods should be running, and the ingress should display an assigned IP address under the ADDRESS column.

### If the application pods are failing, check their logs to diagnose issues with connecting to the database:

`kubectl logs -n nagp-assignment-pg -l app=spring-boot-service
`

## Sample API Requests
Here are sample curl commands to demonstrate how to interact with the API after the application has been deployed and is accessible.

### Create Employee:

`curl -X 'POST' \
    'http://<your-ingress-ip-address>/api/employees?departmentName=Sales' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
    "firstName": "Naveen",
    "lastName": "Negi",
    "email": "naveen@gmail.com",
    "mobileNumber": "9900990009",
    "employeeType": "FULL_TIME"
}'`

### Create Department:

`curl -X 'POST' \
    'http://<your-ingress-ip-address>/api/departments' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
    "departmentName": "Sales"
}'`

### To view all Departments:

`curl -X 'GET' \
'http://<your-ingress-ip-address>/api/departments' \
-H 'accept: */*'`

### To view all Emplpoyees:

`curl -X 'GET' \
'http://<your-ingress-ip-address>/api/employees' \
-H 'accept: */*'`

### To view particular Department by id:

`curl -X 'GET' \
'http://<your-ingress-ip-address>/api/departments/<department_id>' \
-H 'accept: */*'`

### To view particular Employee by id:

`curl -X 'GET' \
'http://<your-ingress-ip-address>/api/employees/<employee_id>' \
-H 'accept: */*'`

### Demo Video Link:

[Video Link](https://teams.microsoft.com/l/meetingrecap?driveId=b%21T51DdbIphU-7RwClyC0W4fW5mswHXoVGleb_fM9b2anBPs0xLhkbRL246F_WiXh2&driveItemId=0154MWSDEZLEWICQXDSBCJLAN6LMNBCR3D&sitePath=https%3A%2F%2Fnagarro-my.sharepoint.com%2F%3Av%3A%2Fp%2Fabhay_singh06%2FEZlZLIFC45BElYG-WxoRR2MBLE6SYRYwcfxahIy4edgMYA&fileUrl=https%3A%2F%2Fnagarro-my.sharepoint.com%2Fpersonal%2Fabhay_singh06_nagarro_com%2FDocuments%2FRecordings%2FNAGP%2520Assignment%2520Recording-20250803_135450-Meeting%2520Recording.mp4%3Fweb%3D1&iCalUid=040000008200E00074C5B7101A82E0080000000039C7F5FD4E04DC010000000000000000100000009B9082CE5B78904A8B448A510A861095&threadId=19%3Ameeting_MWYzNzYyMjYtZTc4NC00MmU3LWIzNjEtNTU1NDY5YjY1NmE5%40thread.v2&organizerId=dd39e65b-ee53-42df-b6b7-f7a7dabf2875&tenantId=a45fe71a-f480-4e42-ad5e-aff33165aa35&callId=720474ba-5b9d-4e1c-b937-0d941e40b792&threadType=Meeting&meetingType=Scheduled&subType=RecapSharingLink_RecapCore)