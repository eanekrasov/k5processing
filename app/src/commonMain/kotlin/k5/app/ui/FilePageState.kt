package k5.app.ui

//import k5.app.data.EmptyTextLines
//import k5.app.data.isCode
//import k5.app.platform.File
//import k5.app.platform.textLines
//import kotlinx.coroutines.CoroutineScope
//
//class FilePageState(val file: File) : Page {
//    val lines = { scope: CoroutineScope -> Lines(runCatching { file.textLines(scope) }.getOrDefault(EmptyTextLines), file.isCode()) }
//    override val title = file.name
//    override val isCloseable = true
//    override fun hashCode() = file.path.hashCode()
//    override fun equals(other: Any?) = this === other || other is FilePageState && file.path == other.file.path
//}
