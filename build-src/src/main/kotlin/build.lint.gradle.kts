import build.setup.*

plugins {
//    id("java-library")
    kotlin("jvm")
    id("com.android.lint")
}

lint {
    htmlReport = true
    htmlOutput = file("lint-report.html")
    textReport = true
    absolutePaths = false
    ignoreTestSources = true
}

dependencies {
    compileOnly(libs.android.tools.lint.api)
    // You typically don't need this one:
    compileOnly(libs.android.tools.lint.checks)
    testImplementation(libs.junit)
    testImplementation(libs.android.tools.lint)
    testImplementation(libs.android.tools.lint.tests)
}
