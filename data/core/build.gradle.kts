plugins {
    alias(libs.plugins.commondnd.jvm.library)
    alias(libs.plugins.commondnd.hilt)
}

dependencies {

    implementation(libs.coroutines.core)
}