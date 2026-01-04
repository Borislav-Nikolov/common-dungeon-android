plugins {
    alias(libs.plugins.commondnd.jvm.library)
    alias(libs.plugins.commondnd.hilt)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {

    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
}