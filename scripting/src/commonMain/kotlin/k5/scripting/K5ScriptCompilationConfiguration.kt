package k5.scripting

import k5.math.k5Random
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.script.experimental.api.RefineScriptCompilationConfigurationHandler
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptAcceptedLocation
import kotlin.script.experimental.api.ScriptCollectedData
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptConfigurationRefinementContext
import kotlin.script.experimental.api.ScriptSourceAnnotation
import kotlin.script.experimental.api.acceptedLocations
import kotlin.script.experimental.api.asSuccess
import kotlin.script.experimental.api.collectedAnnotations
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.api.dependencies
import kotlin.script.experimental.api.ide
import kotlin.script.experimental.api.onSuccess
import kotlin.script.experimental.api.refineConfiguration
import kotlin.script.experimental.api.with
import kotlin.script.experimental.dependencies.CompoundDependenciesResolver
import kotlin.script.experimental.dependencies.DependsOn
import kotlin.script.experimental.dependencies.FileSystemDependenciesResolver
import kotlin.script.experimental.dependencies.Repository
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver
import kotlin.script.experimental.dependencies.resolveFromScriptSourceAnnotations
import kotlin.script.experimental.jvm.JvmDependency
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

object K5ScriptCompilationConfiguration : ScriptCompilationConfiguration({
    /**
     * Configure dependencies for compilation, they should contain at least the script base class and its dependencies.
     */
    jvm {
        dependenciesFromCurrentContext(
            "kotlin-stdlib",
            wholeClasspath = true,
        )
    }

    ide {
        acceptedLocations(ScriptAcceptedLocation.Everywhere)
    }

    defaultImports(
        DependsOn::class,
        Repository::class,
        androidx.compose.ui.graphics.Color::class,
    )
    defaultImports("k5.math.k5Random")

    refineConfiguration {
        onAnnotations(
            DependsOn::class,
            Repository::class,
            handler = K5ScriptAnnotationsHandler,
        )
    }
})

object K5ScriptAnnotationsHandler : RefineScriptCompilationConfigurationHandler {
    private val resolver = CompoundDependenciesResolver(
        FileSystemDependenciesResolver(),
        MavenDependenciesResolver(),
    )

    override operator fun invoke(context: ScriptConfigurationRefinementContext): ResultWithDiagnostics<ScriptCompilationConfiguration> {
        val annotations = context.collectedData?.get(ScriptCollectedData.collectedAnnotations)
        return when {
            annotations.isNullOrEmpty() -> context.compilationConfiguration.asSuccess()
            else -> resolver.withAnnotations(annotations).onSuccess {
                context.compilationConfiguration.withDependency(JvmDependency(it)).asSuccess()
            }
        }
    }

    private fun CompoundDependenciesResolver.withAnnotations(annotations: List<ScriptSourceAnnotation<*>>) = runBlocking {
        resolveFromScriptSourceAnnotations(annotations)
    }

    private fun ScriptCompilationConfiguration.withDependency(dependency: JvmDependency) = with {
        dependencies.append(dependency)
    }
}
