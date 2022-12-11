plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "me.omico.lux.compose.material3.pullrefresh"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = properties["project.compose.compiler.version"] as String
    }
}

dependencies {
    compileOnly(platform("androidx.compose:compose-bom:${properties["project.compose.bom.version"]}"))
    compileOnly("androidx.compose.foundation:foundation")
    compileOnly("androidx.compose.material3:material3")
    compileOnly("androidx.compose.ui:ui")
}
