package build.setup

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

data class SourceSetBundle(val project: Project, val main: KotlinSourceSet, val test: KotlinSourceSet) {
    operator fun plus(other: SourceSetBundle) = this + setOf(other)
    operator fun plus(other: Set<SourceSetBundle>) = setOf(this) + other

    infix fun dependsOn(other: SourceSetBundle) {
        main.dependsOn(other.main)
        test.dependsOn(other.test)
    }

    operator fun provideDelegate(thisRef: Any?, property: KProperty<*>) = project.bundle(property.name).let { bundle ->
        bundle dependsOn this
        ReadOnlyProperty { _: Any?, _ -> bundle }
    }
}

internal fun Project.bundle(name: String) = SourceSetBundle(this, sourceSet("${name}Main"), sourceSet("${name}Test"))
internal fun Project.sourceSet(name: String) = multiplatformExtension.sourceSets.maybeCreate(name)
infix fun Iterable<SourceSetBundle>.dependsOn(other: Iterable<SourceSetBundle>) = forEach { l -> other.forEach { r -> l dependsOn r } }
infix fun SourceSetBundle.dependsOn(other: Iterable<SourceSetBundle>) { listOf(this) dependsOn (other) }
infix fun Iterable<SourceSetBundle>.dependsOn(other: SourceSetBundle) { (this) dependsOn listOf(other) }
