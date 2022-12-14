@file:Suppress("UnstableApiUsage")

rootProject.name = "androidx-compose-material3-pullrefresh"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal {
            content {
                includeGroupByRegex("com.gradle.*")
            }
        }
        plugins {
            id("com.android.library") version "7.3.1"
            kotlin("android") version "1.7.21"
        }
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
    id("com.gradle.enterprise") version "3.11.4"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(!gradle.startParameter.isOffline)
    }
}

include("library")
