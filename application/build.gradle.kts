import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.named<Jar>("jar") {
    archiveBaseName.set("purchase-cart-service")
}
tasks.named<BootJar>("bootJar") {
    archiveBaseName.set("purchase-cart-service")
}

plugins {
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.spring") version "1.9.25"
}

configurations {
    implementation {
        exclude(module = "spring-boot-starter-tomcat")
        exclude("com.squareup.okhttp3:okhttp")
    }
}

dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
