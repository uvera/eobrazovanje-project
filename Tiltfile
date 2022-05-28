# -*- mode: Python -*-

load('ext://restart_process', 'docker_build_with_restart')
update_settings(suppress_unused_image_warnings=["java-backend-image"])
update_settings(suppress_unused_image_warnings=["angular-frontend-image"])

gradlew = "./backend/gradlew"
if os.name == "nt":
  gradlew = "backend/gradlew.bat"

local_resource(
  'java-backend-compile',
  gradlew + ' -p backend bootJar && ' +
  'rm -rf backend/build/jar-staging && ' +
  'java -Djarmode=layertools -jar backend/build/libs/e-obrazovanje-0.1.0.jar extract --destination backend/build/jar-extracted && ' +
  'rsync --delete --inplace --checksum -r backend/build/jar-extracted/ backend/build/jar',
  deps=['backend/src', 'backend/build.gradle'],
  )

docker_build_with_restart(
  'java-backend-image',
  './backend/build/jar',
  entrypoint=['java', 'org.springframework.boot.loader.JarLauncher'],
  dockerfile='./backend/dev.Dockerfile',
  live_update=[
    sync('./backend/build/jar/dependencies', '/app'),
    sync('./backend/build/jar/spring-boot-loader', '/app'),
    sync('./backend/build/jar/snapshot-dependencies', '/app'),
    sync('./backend/build/jar/application', '/app'),
  ],
)

docker_build(
    'angular-frontend-image',
    './frontend',
    dockerfile='./frontend/dev.Dockerfile',
    live_update=[
      fall_back_on(['./frontend/package.json', './frontend/package-lock.json']),
      sync('./frontend', '/app'),
    ],
    )

docker_build(
    'rails-app-image',
    './image-eobra-service',
    dockerfile='./image-eobra-service/Dockerfile.prod'
    )

k8s_yaml('kubernetes.yaml')
k8s_yaml('postgresql.k8s.yaml')
k8s_yaml('postgresql.service.k8s.yaml')
k8s_resource('java-backend', port_forwards=8080,
             resource_deps=['java-backend-compile', 'postgresql-db'],
             )
k8s_resource('angular-frontend', port_forwards=4200)
k8s_resource('rails-app', port_forwards=8081)
k8s_resource('postgresql-db', port_forwards=5432)

k8s_resource('minio-db', port_forwards=['9000','9001'])

k8s_yaml('minio.k8s.yaml')
k8s_yaml('minio.service.k8s.yaml')
k8s_yaml('minio.console.service.k8s.yaml')
