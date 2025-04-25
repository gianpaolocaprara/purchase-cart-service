tasks.named<Jar>("jar") {
    archiveBaseName.set("purchase-cart-service-infrastructure-persistence")
}

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":domain"))
    implementation("com.h2database:h2")
}