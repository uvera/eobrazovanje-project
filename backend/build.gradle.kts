import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("com.palantir.docker")
}

group = "io.uvera"
version = "0.1.0"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    all {
        exclude(group = "org.apache.tomcat")
        exclude(module = "spring-boot-starter-tomcat")
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val jjwtVersion: String by project
val springDocVersion: String by project
val testcontainersVersion: String by project

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    // region jwt
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")
    // endregion jwt
    // spring doc
    implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-data-rest:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//region DockerSetup

val bootJarTask = tasks.bootJar.get()
val archivePath = bootJarTask.archiveFileName.get()
val dockerFilePath = "${projectDir.path}/docker/Dockerfile"
val appName = project.name.toLowerCase()
val projectName = "${project.group}/${appName}"
val fullName = "$projectName:${project.version}"
val dockerBuildArgs = mapOf(
    "JAR_FILE" to archivePath
)

// workaround from https://github.com/palantir/gradle-docker/issues/413
tasks.docker {
    inputs.file(dockerFilePath)
}

docker {
    name = fullName
    tag("latest", "$projectName:latest")
    pull(true)
    setDockerfile(file(dockerFilePath))
    files(bootJarTask.outputs)
    buildArgs(dockerBuildArgs)
}

//endregion DockerSetup
