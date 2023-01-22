package k5.scripting

import k5.K5
import java.io.File
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.api.constructorArgs
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

class K5ScriptHost {
    private val scriptingHost = BasicJvmScriptingHost()

    /**
     * Evaluated the given script [script] against the given [k5].
     */
    fun eval(script: SourceCode, k5: K5): ResultWithDiagnostics<EvaluationResult> {
        return scriptingHost.evalWithTemplate<K5Script>(
            script = script,
            evaluation = { constructorArgs(k5) },
        )
    }
}

fun eval(vararg files: File, k5: K5) {
    val scriptingHost = K5ScriptHost()
    files.asSequence().forEach { file ->
        val result = scriptingHost.eval(file.toScriptSource(), k5)
        result.reports.forEach { report ->
            report.logReport()
        }
    }
}

fun K5.evalWithLog(script: String) = K5ScriptHost().eval(script.toScriptSource(), this).reports.forEach { it.logReport() }

private fun ScriptDiagnostic.logReport() = when (severity) {
    ScriptDiagnostic.Severity.DEBUG -> {}
    else -> println(listOfNotNull(message, exception).joinToString(": "))
}