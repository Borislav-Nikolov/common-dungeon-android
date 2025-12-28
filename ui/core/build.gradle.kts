plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.android.library.compose)
}

android {
    namespace = "com.commondnd.ui.core"
}

dependencies {

    implementation(projects.data.user)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.icons)
    implementation(libs.coil.compose)
    implementation(libs.coil.networkHttp)
}
