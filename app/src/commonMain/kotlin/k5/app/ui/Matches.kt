package k5.app.ui

//import androidx.compose.foundation.layout.Arrangement.spacedBy
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.Icon
//import androidx.compose.material.IconButton
//import androidx.compose.material.icons.Icons.Default
//import androidx.compose.material.icons.filled.Download
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.unit.dp
//import k5.app.data.Match
//import k5.app.data.Matches
//import k5.app.ui.AppTheme.code
//
//@Composable
//internal fun Matches(matches: Matches, modifier: Modifier = Modifier, onClick: (Match) -> Unit = {}) {
//    ScrollColumn(modifier) {
//        val maxNum = "${matches.numFound}"
//        items(matches.matches) { item ->
//            Row(Modifier.height(1.6f), spacedBy(16.dp)) {
//                LineIndex(item.index, maxNum)
//                ScaledText(
//                    buildAnnotatedString {
//                        withStyle(code.simple) { append(item.group) }
//                        append(":")
//                        withStyle(code.simple) { append(item.name) }
//                        append(":")
//                        withStyle(code.simple) { append(item.version) }
//                    },
//                    Modifier.weight(1f).unlimitedWidth().padding(12.dp, 0.dp)
//                )
//                IconButton({ onClick(item) }) {
//                    Icon(Default.Download, null, Modifier.size(24.dp).padding(4.dp))
//                }
//            }
//        }
//    }
//}
