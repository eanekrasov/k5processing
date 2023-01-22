package k5.scripting

import k5.K5
import kotlin.script.experimental.annotations.KotlinScript

@KotlinScript(
    displayName = "k5",
    fileExtension = "k5.kts",
    compilationConfiguration = K5ScriptCompilationConfiguration::class
)
abstract class K5Script(k5: K5) : K5 by k5
