package me.omico.compose.project.internal

import me.omico.gradm.dependency.Androidx
import me.omico.gradm.Versions as GradmVersions

internal object ProjectVersions {
    const val COMPOSE_BOM = "project.compose.bom.version"
    const val COMPOSE_COMPILER = "project.compose.compiler.version"
    const val COMPOSE_MATERIAL = "project.compose.material.version"
    const val ANDROID_GRADLE_PLUGIN = "project.android.gradle.plugin.version"
    const val KOTLIN = "project.kotlin.version"
}

internal fun Map<String, String>.versionFor(key: String): String =
    getOrElse(key) { error("Property [$key] not found.") }

internal fun buildVersions(): Map<String, String> =
    mapOf(
        ProjectVersions.COMPOSE_BOM to GradmVersions.androidx.compose.bom,
        ProjectVersions.COMPOSE_COMPILER to GradmVersions.androidx.compose.compiler,
        ProjectVersions.COMPOSE_MATERIAL to Androidx.compose.material.split(":").last(),
        ProjectVersions.ANDROID_GRADLE_PLUGIN to GradmVersions.plugins.android,
        ProjectVersions.KOTLIN to GradmVersions.kotlin,
    )
