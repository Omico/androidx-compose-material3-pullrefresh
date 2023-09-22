plugins {
    kotlin("android") apply false
    kotlin("multiplatform") apply false
    id("com.android.library") apply false
    id("androidx-compose-material3-pullrefresh.root")
}

if (pluginManager.hasPlugin("androidx-compose-material3-pullrefresh.root")) {
    apply(plugin = "androidx-compose-material3-pullrefresh.root")
}
