plugins {
    alias(libs.plugins.commondnd.jvm.library)
    alias(libs.plugins.commondnd.hilt)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {

    implementation(projects.data.networking)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coroutines.core)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinxSerialization)
}
