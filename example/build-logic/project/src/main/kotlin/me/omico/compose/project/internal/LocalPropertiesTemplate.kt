package me.omico.compose.project.internal

import java.io.File

internal fun generateLocalPropertiesTemplate(
    localPropertiesTemplateFile: File,
    versions: Map<String, String>,
) {
    val androidxComposeBomVersion = versions.versionFor(ProjectVersions.COMPOSE_BOM)
    val androidxComposeCompilerVersion = versions.versionFor(ProjectVersions.COMPOSE_COMPILER)
    val androidxComposeMaterialVersion = versions.versionFor(ProjectVersions.COMPOSE_MATERIAL)
    val androidGradlePluginVersion = versions.versionFor(ProjectVersions.ANDROID_GRADLE_PLUGIN)
    val kotlinVersion = versions.versionFor(ProjectVersions.KOTLIN)
    localPropertiesTemplateFile.writeText(
        """
            |# Change these to fit your project's needs
            |# Note $androidxComposeBomVersion contains androidx.compose.material:material:$androidxComposeMaterialVersion
            |# See https://developer.android.com/jetpack/androidx/releases/compose-material#$androidxComposeMaterialVersion
            |# See also, https://developer.android.com/jetpack/compose/bom/bom-mapping
            |# Kotlin $kotlinVersion require compose compiler $androidxComposeCompilerVersion
            |# See https://developer.android.com/jetpack/androidx/releases/compose-compiler#$androidxComposeCompilerVersion
            |project.android.gradle.plugin.version=$androidGradlePluginVersion
            |project.compose.bom.version=$androidxComposeBomVersion
            |project.compose.compiler.version=$androidxComposeCompilerVersion
            |project.kotlin.version=$kotlinVersion
            |
        """.trimMargin(),
    )
}
