package build.setup

import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget

internal fun KotlinTarget.getSourceSetBundle() = when {
    compilations.isEmpty() -> project.bundle(name)
    else -> SourceSetBundle(project, compilations["main"].defaultSourceSet, compilations["test"].defaultSourceSet)
}

internal fun Iterable<KotlinTarget>.toSourceSetBundles() =
    filter { it.platformType != KotlinPlatformType.common }.map { it.getSourceSetBundle() }.toSet()
