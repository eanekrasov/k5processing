import build.setup.*
import build.compose.enableComposeCompilerReports

plugins {
    id("build.library")
    id("org.jetbrains.compose")
//    kotlin("plugin.parcelize")
//    id("build.koin")
}

//android {
//    buildFeatures.compose = true
//    composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
//}

enableComposeCompilerReports()

kotlin {
    sourceSets.all {
        languageSettings.optIn("com.arkivanov.decompose.ExperimentalDecomposeApi")
        languageSettings.optIn("com.russhwolf.settings.ExperimentalSettingsApi")
        languageSettings.optIn("com.russhwolf.settings.ExperimentalSettingsImplementation")
    }
    setupSourceSets {
//        val android by common
//        val desktop by common
        val nonAndroid by common
        nonAndroidSet dependsOn nonAndroid
//        val native by common
//        val darwin by native
//        val ios by darwin
//        iosSet dependsOn ios
//        val macos by darwin
//        macosSet dependsOn macos

        common.main.dependencies {
            implementation(libs.coroutines.core)
            implementation(libs.serialization.json)
            implementation(libs.collections.immutable)
            implementation(libs.bundles.multiplatform.settings)
        }
//        android.main.dependencies {
//            val composeBom = dependencies.platform(libs.androidx.compose.bom)
//            implementation(composeBom)
//            androidTestImplementation(composeBom)
//            implementation(libs.androidx.activity.ktx)
//            implementation(libs.androidx.activity.compose)
//            implementation(libs.bundles.androidx.compose.full)
//            implementation(libs.accompanist.permissions)
//            implementation(libs.androidx.lifecycle.common)
//        }
        nonAndroid.main.dependencies {
            implementation(libs.bundles.jetbrains.compose.full)
        }
    }
}
