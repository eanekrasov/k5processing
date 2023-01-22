import build.setup.*

plugins {
    id("build.ksp")
}

dependencies {
    commonMainApi(libs.koin.core)
    commonMainApi(libs.koin.test)
//    commonMainApi(libs.koin.annotations)
    kspCommonMainMetadata(libs.koin.ksp.compiler)
//    kspAndroid(libs.koin.ksp.compiler)
//    kspIosX64(libs.koin.ksp.compiler)
//    kspIosArm64(libs.koin.ksp.compiler)
}
