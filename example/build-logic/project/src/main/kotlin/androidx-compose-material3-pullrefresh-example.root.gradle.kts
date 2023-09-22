import me.omico.consensus.dsl.requireRootProject

plugins {
    id("androidx-compose-material3-pullrefresh-example.gradm")
    id("androidx-compose-material3-pullrefresh-example.root.git")
    id("androidx-compose-material3-pullrefresh-example.root.spotless")
}

requireRootProject()

consensus {
    allprojects {
        group = gradleProperty("project.group")
        version = gradleProperty("project.version")
    }
}

val wrapper: Wrapper by tasks.named<Wrapper>("wrapper") {
    gradleVersion = versions.gradle
    distributionType = Wrapper.DistributionType.BIN
}

tasks.spotlessApply {
    dependsOn(gradle.includedBuild("androidx-compose-material3-pullrefresh").task(":spotlessApply"))
}
