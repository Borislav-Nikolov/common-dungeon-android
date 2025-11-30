plugins {
    alias(libs.plugins.commondnd.android.library)
    alias(libs.plugins.commondnd.hilt)
}

android {
    namespace = "com.commondnd.data.storage"
}

dependencies {

    implementation(libs.androidx.datastore.preferences)
}
