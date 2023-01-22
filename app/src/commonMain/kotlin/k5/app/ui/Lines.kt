package k5.app.ui

//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.unit.dp
//import k5.app.data.Line
//import k5.app.data.TextLines
//import k5.app.ui.AppTheme.code

//class Lines(
////    private val textLines: TextLines,
//    private val isCode: Boolean
//) {
//    val lineNumberDigitCount get() = textLines.size.toString().length
//    val size get() = textLines.size
//    operator fun get(index: Int) = Line(index + 1, textLines[index], isCode)
//}
//
//@Composable
//internal fun Lines(lines: Lines, modifier: Modifier = Modifier) {
//    Box(Modifier.softWrapLine()) {
//        val maxNum = remember(lines.lineNumberDigitCount) { "9".repeat(lines.lineNumberDigitCount) }
//        ScrollColumn(modifier.fillMaxSize().softWrapLine()) {
//            items(lines.size) { index ->
//                val line = lines[index]
//                Row(modifier.height(1.6f)) {
//                    LineIndex(line.number, maxNum)
//                    ScaledText(
//                        buildAnnotatedString {
//                            withStyle(code.simple) {
//                                val text = line.text.replace("\t", "    ").also(::append)
//                                if (line.isCode) for ((style, regexp) in styles) {
//                                    for (result in regexp.findAll(text)) {
//                                        addStyle(style, result.range.first, result.range.last + 1)
//                                    }
//                                }
//                            }
//                        },
//                        Modifier.weight(1f).unlimitedWidth().padding(start = 28.dp, end = 12.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//private val styles = listOf(
//    code.punctuation to Regex("[(){}\\[\\]\"=:,]"),
//    code.keyword to Regex("(var|fun|actual|internal|import|object|private|enum class|return|class) "),
//    code.keyword to Regex("(val|for|expect|override|sealed|public|package|abstract|interface|open) "),
//    code.value to Regex("(true|false|[0-9]*)"),
//    code.annotation to Regex("^@[a-zA-Z_]+ "),
//    code.comment to Regex("^\\s*//.*"),
//)
