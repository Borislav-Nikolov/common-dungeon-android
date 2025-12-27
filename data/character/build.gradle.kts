plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.commondnd.data.character"
}

dependencies {

    implementation(projects.data.storage)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coroutines.core)
}
