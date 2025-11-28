plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.android.library.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.commondnd.hilt)
}

android {
    namespace = "com.commondnd.ui.navigation"
}

dependencies {

    implementation(projects.ui.material3)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.viewmodel.navigation3)
    implementation(libs.androidx.compose.material3.adaptive.navigation3)
}
