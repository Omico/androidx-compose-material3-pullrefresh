@file:Suppress("UnstableApiUsage")

import java.util.Properties

pluginManagement {
    val localProperties = Properties().apply {
        val file = rootDir.resolve("local.properties")
        require(file.exists()) { "${file.absolutePath} doesn't exists. Please follow the instruction!!!" }
        load(file.inputStream())
    }
    gradle.startParameter.projectProperties["project.compose.compiler.version"] =
        localProperties["project.compose.compiler.version"] as String
    repositories {
        if (gradle.parent != null) maven("https://maven.omico.me")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.library") version localProperties["project.android.gradle.plugin.version"] as String
        id("org.jetbrains.compose") version localProperties["project.compose.multiplatform.version"] as String
        kotlin("android") version localProperties["project.kotlin.version"] as String
        kotlin("multiplatform") version localProperties["project.kotlin.version"] as String
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

if (gradle.parent != null) {
    includeBuild("example/build-logic/project")
}
