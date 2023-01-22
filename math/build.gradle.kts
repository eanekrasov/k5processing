import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("build.library")
}

//android {
//    namespace = "k5.math"
//}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-Xskip-prerelease-check"
}