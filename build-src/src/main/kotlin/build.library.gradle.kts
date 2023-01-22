import build.setup.*
import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("multiplatform")
//    id("com.android.library")
//    kotlin("plugin.parcelize")
    kotlin("plugin.serialization")
//    id("build.koin")
}

setupMultiplatform {
//    android()
    jvm("desktop")
//    js(IR) { browser() }
//    iosX64()
//    iosArm64()
//    iosSimulatorArm64()
//    macosX64()
//    macosArm64()
}

setupBinaryCompatibilityValidator()

//android {
//    sourceSets["main"].setRoot("src/androidMain")
//    lint {
//        checkDependencies = true
//    }
//}

/** Package the given lint checks library into this AAR  */
//dependencies {
//    implementation(project(":checks"))
//    lintPublish(project(":checks"))
//}

kotlin {
    sourceSets.all {
        languageSettings.optIn("com.arkivanov.decompose.ExperimentalDecomposeApi")
        languageSettings.optIn("com.russhwolf.settings.ExperimentalSettingsApi")
        languageSettings.optIn("com.russhwolf.settings.ExperimentalSettingsImplementation")
    }
    setupSourceSets {
//        val android by common
        val desktop by common
//        val native by common
//        val darwin by native
//        val ios by darwin
//        iosSet dependsOn ios
//        val macos by darwin
//        macosSet dependsOn macos
//        val nonAndroid by common
//        nonAndroidSet dependsOn nonAndroid

        common.main.dependencies {
            implementation(libs.collections.immutable)
            implementation(libs.napier)
            implementation(libs.coroutines.core)
            implementation(libs.serialization.json)
            implementation(libs.bundles.multiplatform.settings)
        }
        desktop.main.dependencies {
            implementation(libs.bundles.jetbrains.compose.full)
        }
//        android.main.dependencies {
//            implementation(libs.coroutines.android)
//            implementation(libs.androidx.activity.ktx)
//            implementation(libs.androidx.lifecycle.common)
//        }
        common.test.dependencies {
            implementation(libs.kotlin.test)
        }
        desktop.test.dependencies {
            implementation(libs.mockito.kotlin)
            implementation(libs.kotlin.reflect)
            implementation(libs.assertj.core)
        }
//        android.test.dependencies {
//            implementation(libs.junit.jupiter.api)
//            implementation(libs.junit.jupiter.engine)
//            implementation(libs.junit.jupiter.params)
//            implementation(libs.junit.platform.suite)
//        }
    }
}
