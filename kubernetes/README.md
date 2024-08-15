# Create Kubernetes Test Cluster Using kind

Create cluster:

```sh
$ cd into/this/directory
$ kind create cluster --name acse --config kind/config.yaml

$ kubectl get nodes
$ kubectl get all --all-namespaces
```

Install Ingress controller:

```sh
$ kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.10.1/deploy/static/provider/kind/deploy.yaml
$ watch kubectl get all -n ingress-nginx
```

Load images into kind (if not pushed to container image registry):
```
    We have pushed images group-04-mymakler-svc-api:0.0.1, group-04-mymakler-svc-frauddetection:0.0.1, group-04-mymakler-ui-web:0.0.1, on Dockerhub
    and loaded the images directly rather than building.
    Dockerhub repo: 
    1. salmanzebmalik/group-04-mymakler-svc-api:0.0.1
    2. salmanzebmalik/group-04-mymakler-svc-frauddetection:0.0.1
    3. salmanzebmalik/group-04-mymakler-ui-web:0.0.1
```

```sh
$ kind load docker-image --name acse \
    unims/acse2024/mymakler-svc-api:0.0.1 \
    unims/acse2024/mymakler-svc-frauddetection:0.0.1 \
    unims/acse2024/mymakler-ui-web:0.0.1
```

$ kind load docker-image --name acse unims/acse2024/mymakler-svc-api:0.0.1 unims/acse2024/mymakler-svc-frauddetection:0.0.1 unims/acse2024/mymakler-ui-web:0.0.1

Create application:

```sh
$ kubectl apply -f .
$ watch kubectl get all,ingress,pvc,pv -n mymakler
```

Delete cluster:

```sh
$ kind delete cluster --name acse
```
