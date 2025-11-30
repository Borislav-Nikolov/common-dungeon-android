plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.commondnd.data.player"
}

dependencies {

    implementation(projects.data.core)
    implementation(projects.data.item)
    implementation(projects.data.character)
    implementation(projects.data.networking)
    implementation(projects.data.storage)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coroutines.core)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinxSerialization)
}
