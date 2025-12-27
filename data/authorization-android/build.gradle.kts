plugins {
    alias(libs.plugins.commondnd.android.library)
}

android {
    namespace = "com.commondnd.data.authorization.android"
}

dependencies {

    api(projects.data.authorization)
}
