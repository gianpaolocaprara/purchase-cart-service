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
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("org.assertj:assertj-core:3.24.2")
        testImplementation("io.mockk:mockk:1.13.8")
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
