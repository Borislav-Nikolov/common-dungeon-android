plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.android.library.compose)
}

android {
    namespace = "com.commondnd.ui.material3"
}

dependencies {
    implementation(libs.androidx.compose.material3)
}
