plugins {
    id("build.library")
}

//android {
//    namespace = "k5.scripting"
//}

dependencies {
    commonMainApi(projects.math)
    commonMainApi(projects.processing)
    commonMainApi(libs.coroutines.core)
    commonMainApi(libs.kotlin.scripting.common)
    commonMainApi(libs.kotlin.scripting.jvm)
    commonMainApi(libs.kotlin.scripting.jvm.host)
    commonMainApi(libs.kotlin.scripting.dependencies)
    commonMainApi(libs.kotlin.scripting.dependencies.maven)

    // На случай если получится сделать read-eval-print-loop.
    commonMainRuntimeOnly(libs.kotlin.main.kts)
    commonTestRuntimeOnly(libs.kotlin.main.kts)
    commonMainRuntimeOnly(libs.kotlin.scripting.jsr223)
    commonTestRuntimeOnly(libs.kotlin.scripting.jsr223)

    commonTestImplementation(libs.junit)
}
