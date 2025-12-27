plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.hilt)
}

android {
    namespace = "com.commondnd.ui.sync"
}

dependencies {

    implementation(projects.ui.core)
    implementation(projects.data.core)

    ksp(libs.hilt.ext.compiler)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
}
