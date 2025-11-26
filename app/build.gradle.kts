plugins {
    alias(libs.plugins.commondnd.android.application)
    alias(libs.plugins.commondnd.android.application.compose)
    alias(libs.plugins.commondnd.hilt)
}

android {
    namespace = "com.commondnd"

    defaultConfig {
        applicationId = "com.commondnd"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = "debug"
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {


    // TODO: review which are needed after finishing the build configurations
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.ui)


    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}