package build.setup

import org.gradle.api.NamedDomainObjectCollection
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.kpm.external.project
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.Family

@OptIn(org.jetbrains.kotlin.gradle.kpm.external.ExternalVariantApi::class)
fun KotlinMultiplatformExtension.setupSourceSets(block: MultiplatformSourceSets.() -> Unit) {
    DefaultMultiplatformSourceSets(project, targets, sourceSets).block()
}

interface MultiplatformSourceSets : SourceSets {
    val common: SourceSetBundle
    val allSet: Set<SourceSetBundle>
    val nonAndroidSet: Set<SourceSetBundle>
    val javaSet: Set<SourceSetBundle>
    val nativeSet: Set<SourceSetBundle>
    val darwinSet: Set<SourceSetBundle>
    val iosSet: Set<SourceSetBundle>
    val macosSet: Set<SourceSetBundle>
    val uikitSet: Set<SourceSetBundle>
    fun nativeSourceSets(predicate: KotlinNativeTarget.() -> Boolean): Set<SourceSetBundle>
    fun nativeSourceSets(vararg families: Family): Set<SourceSetBundle>
}

private class DefaultMultiplatformSourceSets(
    private val project: Project,
    private val targets: NamedDomainObjectCollection<KotlinTarget>,
    private val sourceSets: SourceSets,
) : MultiplatformSourceSets, SourceSets by sourceSets {
    override val common get() = project.bundle("common")
    override val allSet = targets.toSourceSetBundles()
    override val nonAndroidSet = sourceSets { platformType !in setOf(KotlinPlatformType.androidJvm) }
    override val javaSet = nativeSourceSets { platformType in setOf(KotlinPlatformType.androidJvm, KotlinPlatformType.jvm) }
    override val nativeSet = nativeSourceSets { true }
    override val darwinSet = nativeSourceSets(Family.IOS, Family.OSX, Family.WATCHOS, Family.TVOS)
    override val iosSet = nativeSourceSets(Family.IOS).prefixedWith("ios")
    override val macosSet = nativeSourceSets(Family.OSX)
    override val uikitSet = nativeSourceSets(Family.IOS).prefixedWith("uikit")

    val nativeTargets get() = targets.filterIsInstance<KotlinNativeTarget>()
    fun sourceSets(predicate: KotlinTarget.() -> Boolean) = targets.filter(predicate).toSourceSetBundles()
    override fun nativeSourceSets(predicate: KotlinNativeTarget.() -> Boolean) = nativeTargets.filter(predicate).toSourceSetBundles()
    override fun nativeSourceSets(vararg families: Family) = nativeSourceSets { konanTarget.family in families }
}

fun Set<SourceSetBundle>.prefixedWith(prefix: String) = filter { it.main.name.startsWith(prefix) }.toSet()
