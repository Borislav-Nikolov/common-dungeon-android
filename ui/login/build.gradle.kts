plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.android.library.compose)
    alias(libs.plugins.commondnd.hilt)
}

android {
    namespace = "com.commondnd.ui.login"
}

dependencies {

    implementation(projects.data.core)
    implementation(projects.data.user)
    implementation(projects.data.authorizationAndroid)
    implementation(projects.ui.navigation)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.hilt.navigation.compose)
}
