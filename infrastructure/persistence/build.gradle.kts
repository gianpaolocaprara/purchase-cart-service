tasks.named<Jar>("jar") {
    archiveBaseName.set("purchase-cart-service-infrastructure-persistence")
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.h2)
}