import me.omico.compose.project.internal.UpstreamSynchronizer
import me.omico.compose.project.internal.buildVersions
import me.omico.compose.project.internal.generateLocalPropertiesTemplate
import me.omico.consensus.dsl.requireRootProject

plugins {
    id("example.gradm")
    id("example.root.git")
    id("example.root.spotless")
}

requireRootProject()

val wrapper: Wrapper by tasks.named<Wrapper>("wrapper") {
    gradleVersion = versions.gradle
    distributionType = Wrapper.DistributionType.BIN
}

val androidxDirectory = consensus.localProperties.getOrNull<File>("androidx.directory")
    ?.takeIf(File::exists)

val localPropertiesTemplateFile = file("library/local.properties.template")
val projectVersions = buildVersions()

val syncUpstream by tasks.registering(UpstreamSynchronizer::class) {
    group = "project"
    enabled = androidxDirectory != null
    androidxDirectory?.run {
        commitIdProperty.set("4c61c4d68d2bf0ccc61e316bc2a03754bb6979cf")
        versionsProperty.set(projectVersions)
        androidxDirectoryProperty.set(androidxDirectory)
        outputSourceDirectoryProperty.set(file("library/src/main/kotlin/androidx/compose/material3/pullrefresh"))
        localPropertiesTemplateFileProperty.set(localPropertiesTemplateFile)
    }
}

generateLocalPropertiesTemplate(
    localPropertiesTemplateFile = localPropertiesTemplateFile,
    versions = projectVersions,
)
