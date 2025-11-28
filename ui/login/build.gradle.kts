plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.android.library.compose)
    alias(libs.plugins.commondnd.hilt)
}

android {
    namespace = "com.commondnd.ui.initial"
}

dependencies {

    implementation(projects.data.user)
    implementation(projects.data.authorization)
    implementation(projects.ui.navigation)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.hilt.navigation.compose)
}
