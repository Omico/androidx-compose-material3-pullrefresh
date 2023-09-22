import me.omico.consensus.dsl.requireRootProject

plugins {
    id("me.omico.consensus.spotless")
}

requireRootProject()

consensus {
    spotless {
        rootProject {
            freshmark()
            gradleProperties()
        }
        subprojects {
            kotlin()
            kotlinGradle()
        }
    }
}

subprojects {
    rootProject.tasks {
        spotlessApply { dependsOn(this@subprojects.tasks.spotlessApply) }
        spotlessCheck { dependsOn(this@subprojects.tasks.spotlessCheck) }
    }
}
