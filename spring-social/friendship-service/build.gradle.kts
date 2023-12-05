plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
}

group = "ca.gbc"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation(project(mapOf("path" to ":")))
    testImplementation(project(mapOf("path" to ":")))
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.testcontainers:testcontainers-bom:1.18.1")
    testImplementation("org.testcontainers:mongodb:1.18.1")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.0.3")
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.1.5")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.2.1")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j:3.0.3")
    // https://mvnrepository.com/artifact/io.micrometer/micrometer-observation
    implementation("io.micrometer:micrometer-observation:1.11.3")
    // https://mvnrepository.com/artifact/io.micrometer/micrometer-tracing-bridge-brave
    implementation("io.micrometer:micrometer-tracing-bridge-brave:1.1.4")
    // https://mvnrepository.com/artifact/io.zipkin.reporter2/zipkin-reporter-brave
    implementation("io.zipkin.reporter2:zipkin-reporter-brave:2.16.4")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
