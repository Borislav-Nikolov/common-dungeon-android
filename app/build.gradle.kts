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
            applicationIdSuffix = ".debug"
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

    implementation(projects.domain)
    implementation(projects.ui.material3)
    implementation(projects.ui.navigation)
    implementation(projects.ui.initial)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}