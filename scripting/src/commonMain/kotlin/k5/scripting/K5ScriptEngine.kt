package k5.scripting

import java.io.Reader
import javax.script.Bindings
import javax.script.ScriptContext
import javax.script.ScriptEngine
import javax.script.ScriptEngineFactory
import javax.script.ScriptEngineManager
import javax.script.SimpleBindings
import javax.script.SimpleScriptContext

class K5ScriptEngine: ScriptEngine {
    override fun eval(script: String?, context: ScriptContext?): Any = ""
    override fun eval(reader: Reader?, context: ScriptContext?): Any = ""
    override fun eval(script: String?): Any = ""
    override fun eval(reader: Reader?): Any = ""
    override fun eval(script: String?, n: Bindings?): Any = ""
    override fun eval(reader: Reader?, n: Bindings?): Any = ""
    override fun put(key: String?, value: Any?) = Unit
    override fun get(key: String?): Any = ""
    override fun getBindings(scope: Int): Bindings = SimpleBindings()
    override fun setBindings(bindings: Bindings?, scope: Int) = Unit
    override fun createBindings(): Bindings = SimpleBindings()
    override fun getContext(): ScriptContext = SimpleScriptContext()
    override fun setContext(context: ScriptContext?) = Unit
    override fun getFactory() = K5ScriptingFactory
    companion object K5ScriptingFactory : ScriptEngineFactory {
        override fun getEngineName() = "k5.kts"
        override fun getEngineVersion() = "1.0.0"
        override fun getExtensions() = mutableListOf("k5.kts")
        override fun getMimeTypes() = mutableListOf<String>()
        override fun getNames() = mutableListOf("k5")
        override fun getLanguageName(): String = "kit"
        override fun getLanguageVersion(): String = "1"
        override fun getParameter(key: String?): Any = ""
        override fun getMethodCallSyntax(obj: String?, m: String?, vararg args: String?): String = ""
        override fun getOutputStatement(toDisplay: String?): String = ""
        override fun getProgram(vararg statements: String?): String = ""
        override fun getScriptEngine(): ScriptEngine = K5ScriptEngine()
    }
}

fun repl() {
    val manager = ScriptEngineManager()
    manager.registerEngineExtension("k5.kts", K5ScriptEngine)
    val engine = manager.getEngineByExtension("k5.kts")!!
    print("> ")
    System.`in`.reader().forEachLine {
        println(engine.eval(it))
        print("\n> ")
    }
}
