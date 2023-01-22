import org.jetbrains.compose.desktop.application.dsl.TargetFormat as Format

plugins {
    id("build.app")
}

dependencies {
    commonMainApi(projects.processing)
    desktopMainApi(projects.processing)
    commonMainApi(projects.scripting)
    commonMainApi(projects.examples)
    commonMainApi(libs.jetbrains.compose.ui.util)
    commonMainApi(libs.jetbrains.compose.foundation.layout)
    commonMainApi(libs.jetbrains.compose.material.icons.core)
    commonMainApi(libs.jetbrains.compose.material.icons.extended)
    desktopMainApi(compose.desktop.currentOs)
}

//android {
//    namespace = "k5.app.android"
//    defaultConfig {
//        applicationId = "k5.app.android"
//        versionCode = 1
//        versionName = "1.0"
//    }
//}

compose.desktop.application {
    mainClass = "k5.app.desktop"
    nativeDistributions {
        targetFormats = setOf(Format.Dmg, Format.Msi, Format.Deb)
        packageName = "k5"
        packageVersion = "1.0.0"
    }
}
