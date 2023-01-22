plugins {
    id("build.library-ui")
}

//android {
//    namespace = "k5.processing"
//}

dependencies {
    commonMainApi(projects.math)
    commonMainApi(libs.jetbrains.compose.ui.util)
    commonMainApi(libs.jetbrains.compose.ui.unit)
}