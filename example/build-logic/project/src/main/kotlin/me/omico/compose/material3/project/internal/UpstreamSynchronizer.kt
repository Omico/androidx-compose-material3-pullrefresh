package me.omico.compose.material3.project.internal

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.internal.ProcessOperations
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import javax.inject.Inject

@UntrackedTask(because = "Not worth tracking")
abstract class UpstreamSynchronizer : DefaultTask() {
    @get:Inject
    abstract val processOperations: ProcessOperations

    @get:InputDirectory
    abstract val androidxDirectoryProperty: DirectoryProperty

    @get:Input
    abstract val commitIdProperty: Property<String>

    @get:OutputDirectory
    abstract val outputDirectoryProperty: DirectoryProperty

    @TaskAction
    fun sync() {
        val androidxDirectory = androidxDirectoryProperty.asFile.get()
        val commitId = commitIdProperty.get()
        val outputDirectory = outputDirectoryProperty.asFile.get()
        processOperations.exec {
            workingDir = androidxDirectory
            commandLine = listOf("git", "fetch", "--all")
        }
        processOperations.exec {
            workingDir = androidxDirectory
            commandLine = listOf("git", "checkout", commitId)
        }
        val upstream = androidxDirectory
            .resolve("compose/material/material/src/commonMain/kotlin/androidx/compose/material/pullrefresh")
        upstream.walk().forEach { upstreamFile ->
            if (upstreamFile.isDirectory) return@forEach
            val relativePath = upstreamFile.relativeTo(upstream)
            val localFile = outputDirectory.resolve(relativePath)
            upstreamFile.copyTo(localFile, overwrite = true)
            println(localFile.absolutePath)
            localFile.readText()
                .contains("    val color = elevationOverlay?.apply(color = backgroundColor, elevation = Elevation)")
                .let(::println)
            localFile.readText()
                .replace(
                    oldValue = "import androidx.compose.material.LocalElevationOverlay\n",
                    newValue = "",
                )
                .replace(
                    oldValue = run {
                        "\n" +
                            "    // Apply an elevation overlay if needed. Note that we aren't using Surface here, as we do not\n" +
                            "    // want its input-blocking behaviour, since the indicator is typically displayed above other\n" +
                            "    // (possibly) interactive content.\n" +
                            "    val elevationOverlay = LocalElevationOverlay.current\n" +
                            "    val color = elevationOverlay?.apply(color = backgroundColor, elevation = Elevation)\n" +
                            "        ?: backgroundColor\n"
                    },
                    newValue = "",
                )
                .replace(
                    oldValue = "            .background(color = color, shape = SpinnerShape)",
                    newValue = "            .background(color = backgroundColor, shape = SpinnerShape)",
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
}
