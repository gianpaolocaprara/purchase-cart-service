tasks.named<Jar>("jar") {
    archiveBaseName.set("purchase-cart-service-infrastructure-persistence")
}

dependencies {
    implementation(project(":domain"))
    implementation("com.h2database:h2:2.1.214")
}