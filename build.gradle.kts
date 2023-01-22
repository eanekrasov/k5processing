import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import build.setup.*

plugins {
    id("build.setup") apply false
    id("com.github.ben-manes.versions")
}

allprojects {
    group = "k5.processing"
    version = "1.0.0"
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") { name = "compose-dev" }
    }
    dependencies {
        classpath(libs.bundles.plugins)
    }
}

setupDefaults {
    android()
    jvm()
//    macosCompat()
//    iosCompat()
//    watchosCompat()
//    tvosCompat()
//    js(BOTH) { browser() }
    setupSourceSets {
        val native by common
        val darwin by native
        val macos by darwin
        macosSet dependsOn macos
//        val ios by darwin
//        iosSet dependsOn ios
    }
}

ensureUnreachableTasksDisabled()

subprojects {
    // ./gradlew dependencyUpdates
    // Report: build/dependencyUpdates/report.txt
    apply(plugin = "com.github.ben-manes.versions")

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.freeCompilerArgs += "-Xskip-prerelease-check"
        //kotlinOptions.languageVersion = "1.8"
    }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        !candidate.version.isStable() && currentVersion.isStable()
    }
}

/*afterEvaluate {
    // a temporary workaround for a bug in jsRun invocation - see https://youtrack.jetbrains.com/issue/KT-48273
    // Workaround for https://youtrack.jetbrains.com/issue/KT-52776
    extensions.findByType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>()?.apply {
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.10.0"
        nodeVersion = "16.0.0"
    }
}*/
