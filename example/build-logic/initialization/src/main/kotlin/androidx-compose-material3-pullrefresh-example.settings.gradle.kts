import me.omico.gradm.addDeclaredRepositories

addDeclaredRepositories()

plugins {
    id("androidx-compose-material3-pullrefresh-example.gradm")
    id("androidx-compose-material3-pullrefresh-example.gradle-enterprise")
}

includeBuild("build-logic/project")
