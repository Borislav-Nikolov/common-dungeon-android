plugins {
    alias(libs.plugins.commondnd.android.application)
    alias(libs.plugins.commondnd.android.application.compose)
    alias(libs.plugins.commondnd.hilt)
    alias(libs.plugins.gms)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.commondnd"

    defaultConfig {
        applicationId = "com.commondnd"
        versionCode = 5
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            versionNameSuffix = "-debug"
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(projects.common)
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
    implementation(projects.ui.sync)
    implementation(projects.ui.web)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.icons)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.hilt.ext.work)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.browser)
}