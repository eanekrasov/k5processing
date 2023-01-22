package build.setup

import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

fun Project.setupDefaults(action: MultiplatformConfigurator? = null) {
    extra.set(DEFAULTS_KEY, listOfNotNull(action))
}

internal inline fun <reified T : Any> Project.requireDefaults(): T = requireNotNull(getDefaults()) { "Defaults not found for type ${T::class}" }

internal inline fun <reified T : Any> Project.getDefaults(): T? = getDefaults { it as? T }

private fun <T : Any> Project.getDefaults(mapper: (Any?) -> T?): T? = getDefaultsList()?.asSequence()?.mapNotNull(mapper)?.firstOrNull() ?: parent?.getDefaults(mapper)

private fun Project.getDefaultsList() = extra.takeIf { it.has(DEFAULTS_KEY) }?.get(DEFAULTS_KEY) as? List<*>?

private const val DEFAULTS_KEY = "build.defaults"
