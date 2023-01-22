@file:Suppress("unused")

package build.compose

import build.compose.ComposeAttribute.Companion.COMPOSE_ATTRIBUTE
import org.gradle.api.Named
import org.gradle.api.Project
import org.gradle.api.attributes.Attribute
import org.gradle.api.attributes.AttributeCompatibilityRule
import org.gradle.api.attributes.CompatibilityCheckDetails
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

interface ComposeAttribute : Named {
    companion object {
        val COMPOSE_ATTRIBUTE: Attribute<ComposeAttribute> = Attribute.of("com.sample.durak.compose", ComposeAttribute::class.java)
        const val TRUE = "true"
        const val FALSE = "false"
    }
}

val composeAttribute: Attribute<String> = Attribute.of("com.sample.durak.compose", String::class.java)

fun Project.applyComposeAttribute() {
    dependencies.attributesSchema {
        attribute(COMPOSE_ATTRIBUTE) {
            compatibilityRules.add(ComposeCompatibilityRule::class.java)
        }
    }
}

abstract class ComposeCompatibilityRule : AttributeCompatibilityRule<ComposeAttribute> {
    override fun execute(t: CompatibilityCheckDetails<ComposeAttribute>) = t.run {
        if (producerValue?.name == "false" && consumerValue?.name == "true") {
            compatible()
        }
    }
}

fun KotlinMultiplatformExtension.uikitX64(name: String = "uikitX64") = iosX64(name) {
    attributes.attribute(
        COMPOSE_ATTRIBUTE,
        project.objects.named<ComposeAttribute>(ComposeAttribute.TRUE)
    )
}

fun KotlinMultiplatformExtension.uikitArm64(name: String = "uikitArm64") = iosArm64(name) {
    attributes.attribute(
        COMPOSE_ATTRIBUTE,
        project.objects.named<ComposeAttribute>(ComposeAttribute.TRUE)
    )
}
