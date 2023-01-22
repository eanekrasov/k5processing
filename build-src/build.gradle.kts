plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") { name = "compose-dev" }
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(gradleKotlinDsl())
    implementation(libs.bundles.plugins)
    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        create("SetupPlugin") {
            id = "build.setup"
            implementationClass = "build.setup.SetupPlugin"
        }
    }
}

kotlin {
    target {
        sourceSets.all {
            languageSettings.optIn("org.jetbrains.kotlin.gradle.kpm.external.ExternalVariantApi")
            languageSettings.optIn("kotlin.contracts.ExperimentalContracts")
        }
    }
}
