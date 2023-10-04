package me.omico.compose.project.internal

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.internal.ProcessOperations
import org.gradle.api.provider.MapProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import java.io.File
import javax.inject.Inject

@UntrackedTask(because = "Not worth tracking")
abstract class UpstreamSynchronizer : DefaultTask() {
    @get:Inject
    abstract val processOperations: ProcessOperations

    @get:Input
    abstract val versionsProperty: MapProperty<String, String>

    @get:InputDirectory
    abstract val androidxDirectoryProperty: DirectoryProperty

    @get:OutputDirectory
    abstract val outputSourceDirectoryProperty: DirectoryProperty

    @get:OutputFile
    abstract val localPropertiesTemplateFileProperty: RegularFileProperty

    @TaskAction
    fun sync() {
        val commitId = "0872f930da585f7fbf6e17c74b1dc42045e8b2c6"
        val androidxDirectory = androidxDirectoryProperty.asFile.get()
        val outputDirectory = outputSourceDirectoryProperty.asFile.get()
        val localPropertiesTemplateFile = localPropertiesTemplateFileProperty.asFile.get()
        val versions = versionsProperty.get()
        processOperations.exec {
            workingDir = androidxDirectory
            commandLine = listOf("git", "fetch", "--all")
        }
        processOperations.exec {
            workingDir = androidxDirectory
            commandLine = listOf("git", "checkout", commitId)
        }
        val upstreamDirectory = androidxDirectory
            .resolve("compose/material/material/src/commonMain/kotlin/androidx/compose/material/pullrefresh")
        upstreamDirectory.walk().forEach { upstreamFile ->
            if (upstreamFile.isDirectory) return@forEach
            transformSourceFile(
                upstreamDirectory = upstreamDirectory,
                upstreamFile = upstreamFile,
                outputDirectory = outputDirectory,
            )
        }
        val androidxComposeBomVersion = versions.versionFor(Versions.COMPOSE_BOM)
        val androidxComposeCompilerVersion = versions.versionFor(Versions.COMPOSE_COMPILER)
        val androidxComposeMaterialVersion = versions.versionFor(Versions.COMPOSE_MATERIAL)
        val androidGradlePluginVersion = versions.versionFor(Versions.ANDROID_GRADLE_PLUGIN)
        val kotlinVersion = versions.versionFor(Versions.KOTLIN)
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

    private fun transformSourceFile(
        upstreamDirectory: File,
        upstreamFile: File,
        outputDirectory: File,
    ) {
        val relativePath = upstreamFile.relativeTo(upstreamDirectory)
        val localFile = outputDirectory.resolve(relativePath)
        upstreamFile.copyTo(localFile, overwrite = true)
        localFile.readText()
            .replace(
                oldValue = "import androidx.compose.material.LocalElevationOverlay\n",
                newValue = "",
            )
            .replace(
                oldValue = "package androidx.compose.material",
                newValue = "package androidx.compose.material3",
            )
            .replace(
                oldValue = "import androidx.compose.material.ExperimentalMaterialApi\n",
                newValue = "",
            )
            .replace(
                oldValue = "@ExperimentalMaterialApi\n",
                newValue = "",
            )
            .replace(
                oldValue = "import androidx.compose.material",
                newValue = "import androidx.compose.material3",
            )
            .replace(
                oldValue = "MaterialTheme.colors.surface",
                newValue = "MaterialTheme.colorScheme.surface",
            )
            .replace(
                // For Surface
                oldValue = "elevation = ",
                newValue = "shadowElevation = ",
            )
            .let(localFile::writeText)
    }
}
