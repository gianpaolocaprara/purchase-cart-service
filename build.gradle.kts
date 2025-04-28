import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.25"
}

kotlin {
    jvmToolchain(21)
}

allprojects {
    repositories {
        mavenCentral()
        group = "com.gianpaolo.caprara"
        version = "0.0.1-SNAPSHOT"
    }
}

subprojects {
    apply(plugin = "kotlin")

    dependencies {
        implementation(rootProject.libs.slf4j.api)
        testImplementation(rootProject.libs.junit5)
        testImplementation(rootProject.libs.assertj.core)
        testImplementation(rootProject.libs.mockk)
    }

    tasks.test {
        useJUnitPlatform()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "21"
        }
    }

}
