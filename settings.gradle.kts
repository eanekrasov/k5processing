/*
 * Copyright here
 */

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "k5processing"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") { name = "compose-dev" }
    }
    plugins {
        id("org.jetbrains.kotlin.multiplatform") version "1.7.20"
        id("org.jetbrains.kotlin.jvm") version "1.7.20"
        id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
        id("org.jetbrains.kotlin.plugin.parcelize") version "1.7.20"
        id("org.jetbrains.compose") version "1.3.0-rc05"
        id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
        id("org.jetbrains.dokka") version "1.7.20"
        id("com.github.ben-manes.versions") version "0.43.0"
    }
}

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") { name = "compose-dev" }
    }
}

buildCache.local {
    isEnabled = true
    isPush = true
    removeUnusedEntriesAfterDays = 5
}

includeBuild("./build-src")

include(":app")
include(":utils")
include(":math")
include(":processing")
include(":examples")
include(":examples")
include(":scripting")
include(":lwjgl")
