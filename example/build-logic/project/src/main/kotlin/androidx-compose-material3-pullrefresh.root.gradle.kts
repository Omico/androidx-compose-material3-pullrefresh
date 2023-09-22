import me.omico.compose.material3.project.internal.UpstreamSynchronizer
import java.io.ByteArrayOutputStream
import kotlin.io.path.Path

plugins {
    id("androidx-compose-material3-pullrefresh-example.root.spotless")
}

allprojects {
    group = "me.omico.compose.material3"
    version = projectVersion
}

val projectVersion: String
    get() = run {
        val version: String = consensus.gradleProperties["project.version"]
        if (version != "snapshot") return@run version
        val outputStream = ByteArrayOutputStream()
        exec {
            commandLine = listOf("git", "rev-parse", "--short", "HEAD")
            standardOutput = outputStream
        }
        outputStream.toString().trim().let { commitId -> "$version.$commitId" }
    }

val androidxDirectory: String? = consensus.localProperties.getOrNull<String?>("androidx.directory")
    ?.replace("\\:", ":")
    ?.replace("\\\\", "\\")
if (androidxDirectory != null && gradle.parent != null) {
    val syncUpstream by tasks.registering(UpstreamSynchronizer::class) {
        group = "project"
        androidxDirectoryProperty.set(Path(androidxDirectory).toFile())
        commitIdProperty.set(consensus.localProperties.get<String>("project.androidx.commitId"))
        outputDirectoryProperty.set(file("material3-pullrefresh/src/commonMain/kotlin/androidx/compose/material3/pullrefresh"))
    }
}
