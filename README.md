# eObrazovanje Project

### Packages needed

- docker (Read docs on setuping docker ([Docs](https://docs.docker.com/)))
- kubectl (Kubernetes CLI tool)
- kind (kubernetes in docker ([Docs](https://kind.sigs.k8s.io/)))
- kubelet ([Docs](https://kubernetes.io/docs/reference/command-line-tools-reference/kubelet/))
- ctlptl ([Docs](https://github.com/tilt-dev/ctlptl))
- java 17 jdk ([BellsoftJDK](https://bell-sw.com/))

### Setup

```console
systemctl enable --now kubelet.service
ctlptl create cluster kind --registry=ctlptl-registry
tilt up
```
