plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.android.library.compose)
}

android {
    namespace = "com.commondnd.ui.home"
}

dependencies {

    implementation(projects.ui.navigation)

    implementation(libs.androidx.compose.material3)
}
