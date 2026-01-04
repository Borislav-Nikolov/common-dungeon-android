plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.android.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.commondnd.ui.core"
}

dependencies {

    implementation(projects.ui.material3)
    implementation(projects.data.core)
    implementation(projects.data.user)
    implementation(projects.data.player)
    implementation(projects.data.character)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.icons)
    implementation(libs.coil.compose)
    implementation(libs.coil.networkHttp)
    implementation(libs.kotlinx.serialization.json)
}
