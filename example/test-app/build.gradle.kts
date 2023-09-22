@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
}

kotlin {
    jvmToolchain(11)
}

android {
    namespace = "me.omico.compose.material3.pullrefresh.test"
    compileSdk = 34
    defaultConfig {
        minSdk = 28
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    composeOptions {
        kotlinCompilerExtensionVersion = versions.androidx.compose.compiler
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("me.omico.compose:compose-material3-pullrefresh")
}

dependencies {
    implementation(androidx.compose.ui)
    implementation(androidx.compose.material3)
    implementation(androidx.activity.compose)
    implementation(material)
}
