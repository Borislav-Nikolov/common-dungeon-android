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
    implementation(projects.data.core)
    implementation(projects.data.user)
    implementation(projects.ui.core)
    implementation(projects.ui.material3)
    implementation(projects.ui.navigation)
    implementation(projects.ui.initial)
    implementation(projects.ui.login)
    implementation(projects.ui.home)
    implementation(projects.ui.characters)
    implementation(projects.ui.inventory)
    implementation(projects.ui.more)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.icons)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.browser)
}