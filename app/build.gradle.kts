import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("kapt")

}

val localProperties = rootProject.file("secret.properties").inputStream().use {
   Properties().apply { load(it) }
}

android {
    namespace = "com.hp.weatherapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "MAPS_API_KEY", "\"${localProperties["MAPS_API_KEY"]}\"")
        buildConfigField("String", "OPENWEATHER_API_KEY", "\"${localProperties["OPENWEATHER_API_KEY"]}\"")
        manifestPlaceholders["MAPS_API_KEY"] = localProperties["MAPS_API_KEY"] as Any

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            // Merge files with the same path
            merges += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)

    // Lifecycle & Coroutines
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.coroutines)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    // Debug-only Compose tooling
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.tooling.preview)

    // Maps
    implementation(libs.maps.compose)

    // Hilt
    implementation(libs.bundles.hilt.impl)
    kapt(libs.bundles.hilt.kapt)

    // Networking
    implementation(libs.bundles.networking)

    // Coil
    implementation(libs.coil)

    // Location
    implementation(libs.location)

    // Permission
    implementation(libs.permissions)

    // Unit Testing
    testImplementation(libs.bundles.testing)

    // Android UI Testing
    androidTestImplementation(libs.bundles.compose.testing)
}

kapt {
    correctErrorTypes = true
}

//  Prevent duplicate annotation classes by resolving to only one
configurations.all {
    resolutionStrategy {
        // Always use the correct version from JetBrains
        force("org.jetbrains:annotations:23.0.0")
    }
    // Exclude the older duplicate from IntelliJ
    exclude(group = "com.intellij", module = "annotations")
}