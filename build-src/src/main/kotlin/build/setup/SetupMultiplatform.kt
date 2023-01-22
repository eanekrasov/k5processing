package build.setup

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.androidJvm
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.jvm
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal typealias SourceSets = NamedDomainObjectContainer<KotlinSourceSet>

internal val Project.multiplatformExtension: KotlinMultiplatformExtension
    get() = kotlinExtension as KotlinMultiplatformExtension

fun Project.setupMultiplatform(targets: MultiplatformConfigurator = requireDefaults()) {
    multiplatformExtension.apply { with(targets) { invoke() } }
//    if (isMultiplatformTargetEnabled(Target.ANDROID)) {
//        extensions.configure<BaseExtension> {
//            compileSdkVersion(33)
//            defaultConfig.minSdk = 26
//        }
//    }
}

fun interface MultiplatformConfigurator {
    operator fun KotlinMultiplatformExtension.invoke()
}

internal enum class Target { ANDROID, JVM }

internal fun Project.isMultiplatformTargetEnabled(target: Target): Boolean = multiplatformExtension.targets.any {
    when (it.platformType) {
        androidJvm -> target == Target.ANDROID
        jvm -> target == Target.JVM
        else -> false
    }
}

//@Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")
//internal val KotlinSourceSet.projectByReflection
//    get() = DefaultKotlinSourceSet::class.memberProperties.first { it.name == "project" }.apply { isAccessible = true }
//        .get(this as DefaultKotlinSourceSet) as Project
