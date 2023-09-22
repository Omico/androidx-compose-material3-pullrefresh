rootProject.name = "androidx-compose-material3-pullrefresh-example-root"

pluginManagement {
    includeBuild("build-logic/initialization")
}

plugins {
    id("androidx-compose-material3-pullrefresh-example")
}

include(":test-app")

includeBuild("..") {
    dependencySubstitution {
        substitute(module("me.omico.compose:compose-material3-pullrefresh"))
            .using(project(":material3-pullrefresh"))
    }
}
