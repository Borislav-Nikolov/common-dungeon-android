plugins {
    alias(libs.plugins.commondnd.android.library)
}

android {
    namespace = "com.commondnd.ui.web"
}

dependencies {

    implementation(libs.androidx.browser)
    implementation(libs.androidx.activity)
}