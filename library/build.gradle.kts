@file:Suppress("UnstableApiUsage")

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    jvmToolchain(11)
    android()
    jvm("desktop")
    sourceSets {
        commonMain {
            dependencies {
                compileOnly(compose.foundation)
                compileOnly(compose.material3)
                compileOnly(compose.ui)
            }
        }
    }
}

android {
    namespace = "me.omico.compose.material3.pullrefresh"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    composeOptions {
        kotlinCompilerExtensionVersion = properties["project.compose.compiler.version"] as String
    }
}
