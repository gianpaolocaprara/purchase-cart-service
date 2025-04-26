import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.named<BootJar>("bootJar") {
    archiveBaseName.set("purchase-cart-service")
}

plugins {
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.spring") version "1.9.25"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":infrastructure:persistence"))

    implementation(libs.spring.boot.web)
    implementation(libs.spring.boot.jdbc)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)

    testImplementation(libs.spring.boot.test)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
