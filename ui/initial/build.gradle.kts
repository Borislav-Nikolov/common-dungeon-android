plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.android.library.compose)
}

android {
    namespace = "com.commondnd.ui.initial"
}

dependencies {

    implementation(projects.data.core)
    implementation(projects.data.user)
    implementation(projects.ui.navigation)
    implementation(projects.ui.login)
    implementation(projects.ui.core)
    implementation(projects.ui.material3)

    implementation(libs.androidx.compose.material3)
}
