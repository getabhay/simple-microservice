Here is the recommended order of operations, along with the commands to verify the status of each resource after it's created.

Step 1: Create the Namespace
It is best practice to create a dedicated namespace first to logically group all your application's resources.

Command:

Bash

kubectl apply -f namespace.yaml
Verification:
After running the command, use kubectl get to confirm the namespace exists:

Bash

kubectl get namespaces
You should see nagp-assignment-pg in the list of namespaces.

Step 2: Create Persistent Storage for the Database
The persistent volume and persistent volume claim must be created before the database deployment. The PVC will wait for a matching PV to bind to before it can be used.

Commands:

Bash

kubectl apply -f db-pv.yaml
kubectl apply -f db-pvc.yaml
Verification:
Check the status of the PersistentVolumeClaim (PVC). Its status should eventually change to Bound.

Bash

kubectl get pvc -n nagp-assignment-pg
If the status is Pending, you can use the describe command to see why it hasn't bound yet:

Bash

kubectl describe pvc mysql-pvc -n nagp-assignment-pg
Step 3: Create Configuration and Secret Resources
These resources contain the non-sensitive configuration and sensitive credentials that your database and application will use. The db-statefulset and deployment files refer to these, so they must be created first.

Commands:

Bash

kubectl apply -f db-secret.yaml
kubectl apply -f db-configmap.yaml
kubectl apply -f db-init-configmap.yaml
Verification:
Confirm that the ConfigMap and Secret resources were created:

Bash

kubectl get configmaps -n nagp-assignment-pg
kubectl get secrets -n nagp-assignment-pg
You should see db-config, db-secret, and db-init-config listed.

Important Note: This step assumes you have already updated your db-init-configmap.yaml file to use VARCHAR(36) instead of UUID to avoid the MySQL syntax error.

Step 4: Deploy the Database
Next, deploy the MySQL database. The db-statefulset and db-service should be applied together.

Commands:

Bash

kubectl apply -f db-service.yaml
kubectl apply -f db-statefulset.yaml
Verification:
Monitor the status of the database pods. It may take some time for the pods to become ready as they need to download the image, create the persistent volume, and run the initialization script.

Bash

kubectl get pods -n nagp-assignment-pg
The pod should eventually show 1/1 in the READY column. If the pod is not running or shows errors, check the logs and events:

Bash

kubectl describe pod <pod-name> -n nagp-assignment-pg
kubectl logs <pod-name> -n nagp-assignment-pg
Step 5: Deploy the Spring Boot Application and Ingress
Finally, deploy your application and the ingress to expose it to external traffic. The application deployment and service depend on the database being available, and the ingress depends on the application's service.

Commands:

Bash

kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl apply -f ingress.yaml
Verification:
Check the status of your application's deployment, pods, and ingress.

Bash

kubectl get deployments -n nagp-assignment-pg
kubectl get pods -n nagp-assignment-pg
kubectl get ingress -n nagp-assignment-pg
All application pods should be running, and the ingress should display an assigned IP address under the ADDRESS column.

If the application pods are failing, check their logs to diagnose issues with connecting to the database:

Bash

kubectl logs -n nagp-assignment-pg -l app=spring-boot-service