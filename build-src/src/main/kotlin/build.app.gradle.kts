import build.setup.*
import org.gradle.kotlin.dsl.*
import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
//    id("com.android.application")
//    kotlin("plugin.parcelize")
    id("org.jetbrains.compose")
}

setupMultiplatform {
//    android()
    jvm("desktop")
}

//@Suppress("UnstableApiUsage")
//android {
//    compileSdk = 33
//    defaultConfig {
//        minSdk = 26
//        targetSdk = 33
//    }
//    sourceSets["main"].setRoot("src/androidMain")
//    packagingOptions {
//        resources.excludes.add("META-INF/*")
//    }
//    applicationVariants.all {
//        val variantName = name
//        sourceSets {
//            getByName("main") {
//                java.srcDir(File("build/generated/ksp/$variantName/kotlin"))
//            }
//        }
//    }
//    lint {
//        checkDependencies.set(true)
//        textReport.set(true)
//        // Produce report for CI:
//        // https://docs.github.com/en/github/finding-security-vulnerabilities-and-errors-in-your-code/sarif-support-for-code-scanning
//        sarifOutput.set(file("../lint-results.sarif"))
//    }
//    dependencies {
//        //desugar utils
//        coreLibraryDesugaring(libs.android.tools.desugar)
//    }
//}

kotlin {
    sourceSets.all {
        languageSettings.optIn("androidx.compose.ui.ExperimentalComposeUiApi")
    }
}
