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

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-gateway
    implementation("org.springframework.cloud:spring-cloud-starter-gateway:4.0.8")
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-client
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.0.3")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
