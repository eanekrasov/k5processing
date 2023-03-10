package build.setup

import com.android.build.gradle.LibraryExtension
import kotlinx.validation.KotlinApiCompareTask
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.setupBinaryCompatibilityValidator() {
    when {

        hasExtension<KotlinMultiplatformExtension>() -> {
            plugins.apply("org.jetbrains.kotlinx.binary-compatibility-validator")
            afterEvaluate {
                tasks.withType<KotlinApiCompareTask> {
                    val target = getTargetForTaskName(taskName = name)
                    if (target != null) {
                        enabled = isMultiplatformApiTargetAllowed(target)
                        println("API check $this enabled=$enabled")
                    }
                }
            }
        }

        hasExtension<LibraryExtension>() -> {
            plugins.apply("org.jetbrains.kotlinx.binary-compatibility-validator")
        }

        else -> error("Unsupported project type for API checks")
    }
}

internal inline fun <reified T : Any> Project.hasExtension(): Boolean = extensions.findByType<T>() != null

private fun getTargetForTaskName(taskName: String): ApiTarget? {
    val targetName = taskName.removeSuffix("ApiCheck").takeUnless { it == taskName } ?: return null
    return when (targetName) {
        "android" -> ApiTarget.ANDROID
        "jvm", "desktop" -> ApiTarget.JVM
        else -> error("Unsupported API check task name: $taskName")
    }
}

private fun Project.isMultiplatformApiTargetAllowed(target: ApiTarget): Boolean = when (target) {
    ApiTarget.ANDROID -> isMultiplatformTargetEnabled(Target.ANDROID)
    ApiTarget.JVM -> isMultiplatformTargetEnabled(Target.JVM)
}

private enum class ApiTarget { ANDROID, JVM, }
