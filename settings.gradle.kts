pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}

rootProject.name = "purchase-cart-service"
include(":application")
include(":domain")
include(":infrastructure:persistence")