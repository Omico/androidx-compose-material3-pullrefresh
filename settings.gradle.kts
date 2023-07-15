@file:Suppress("UnstableApiUsage")

rootProject.name = "androidx-compose-material3-pullrefresh"

pluginManagement {
    val localProperties = java.util.Properties().apply {
        val file = file("local.properties")
        if (!file.exists()) file.createNewFile()
        load(file.inputStream())
    }
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version localProperties["project.android.gradle.plugin.version"] as String
        id("com.android.library") version localProperties["project.android.gradle.plugin.version"] as String
        id("com.gradle.enterprise") version localProperties["project.gradle.enterprise.plugin.version"] as String
        kotlin("android") version localProperties["project.kotlin.version"] as String
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.gradle.enterprise") apply false
}

if (gradle.parent == null) {
    apply(plugin = "com.gradle.enterprise")
    gradleEnterprise {
        buildScan {
            termsOfServiceUrl = "https://gradle.com/terms-of-service"
            termsOfServiceAgree = "yes"
            publishAlwaysIf(!gradle.startParameter.isOffline)
        }
    }

    include("test-app")
}

include("material3-pullrefresh")
project(":material3-pullrefresh").projectDir = file("library")
