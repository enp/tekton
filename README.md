# How to create and run tasks/pipines on Tekton

Any k8s implementation (k0s in docker for example) must be started first:

```
docker run -it --rm --name k0s --hostname k0s --privileged -v /var/lib/k0s -p 6443:6443 k0sproject/k0s
```

Now wait until all pods will be running:

```
docker exec -it k0s kubectl get pods --all-namespaces
```

It is possible to use local kubectl too:

```
docker exec k0s cat /var/lib/k0s/pki/admin.conf > ~/.kube/config
kubectl get pods --all-namespaces
```

Next step is to install and run tekton :

```
kubectl apply -f https://storage.googleapis.com/tekton-releases/operator/latest/release.yaml
kubectl apply -f https://github.com/tektoncd/dashboard/releases/latest/download/tekton-dashboard-release.yaml
```

Now wait until all tekton pods will be running and run proxy to access tekton web dashboard:

```
kubectl proxy --address 0.0.0.0 --disable-filter true
```

Tekton web dashboard can be opened via http://0.0.0.0:8001/api/v1/namespaces/tekton-pipelines/services/tekton-dashboard:http/proxy in any modern browser

Now it is possible to create tasks/pipelines from example files:

```
kubectl apply -f example-task.yaml 
kubectl apply -f example-pipeline.yaml 
```

or from bigtop.bom via groovy script:

```
./bigtop.groovy bigtop.bom | kubectl apply -f -

```

Tekton tasks/pipelines can be started via web dashboard or by [tkn](https://tekton.dev/docs/cli/) tool