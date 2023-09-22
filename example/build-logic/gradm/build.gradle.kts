plugins {
    `kotlin-dsl`
    id("me.omico.gradm") version "4.0.0-beta02"
}

repositories {
    mavenCentral()
}

gradm {
    pluginId = "androidx-compose-material3-pullrefresh-example.gradm"
    debug = true
}
