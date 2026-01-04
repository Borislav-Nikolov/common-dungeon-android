plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.android.library.compose)
    alias(libs.plugins.commondnd.hilt)
}

android {
    namespace = "com.commondnd.ui.characters"
}

dependencies {

    implementation(projects.ui.navigation)
    implementation(projects.ui.core)
    implementation(projects.data.core)
    implementation(projects.data.user)
    implementation(projects.data.player)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.icons)
    implementation(libs.androidx.hilt.navigation.compose)
}
