package build.compose

import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.enableComposeCompilerReports() = tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += composeCompilerReportsArgs()
}

fun Project.composeCompilerReportsArgs() = arrayOf(
    "-P", "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${buildDir.absolutePath}/compose_compiler",
    "-P", "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${buildDir.absolutePath}/compose_compiler",
)
