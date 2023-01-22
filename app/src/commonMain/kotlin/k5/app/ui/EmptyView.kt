package k5.app.ui

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Code
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyView(pages: PagesState, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Row(Modifier.height(1.6f), spacedBy(8.dp)) {
            Icon(Default.Code, null)
            ScaledText("To view file open it from the file tree")
            TextButton({ pages.search() }) { Text("Search") }
//        TextButton({ pages.jarFilesList(pages.filesDir) }) { Text("Search") }
        }
    }
}
