# TODO IMPROVE

### Packages needed

- docker (Read docs on setuping docker)
- kubectl
- kind (kubernetes in docker [Docs](https://kind.sigs.k8s.io/))
- kubelet
- ctlptl
- java 17 jdk

### Setup

```console
systemctl enable --now kubelet.service
ctlptl create cluster kind --registry=ctlptl-registry
tilt up
```
