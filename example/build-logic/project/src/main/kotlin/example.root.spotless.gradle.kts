import me.omico.consensus.dsl.requireRootProject
import me.omico.consensus.spotless.ConsensusSpotlessTokens

plugins {
    id("me.omico.consensus.spotless")
}

requireRootProject()

consensus {
    spotless {
        freshmark()
        gradleProperties()
        kotlin(
            targets = setOf("**/src/*/kotlin/**/*.kt"),
            editorConfigOverride = ConsensusSpotlessTokens.Kotlin.editorConfigOverride + mapOf(
                "ktlint_standard_property-naming" to "disabled",
            ),
        )
        kotlinGradle()
    }
}
