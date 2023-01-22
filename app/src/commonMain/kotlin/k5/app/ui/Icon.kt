package k5.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

//@Composable
//fun Icon(item: TreeItem) {
//    when (item.file.isDirectory) {
//        true -> FolderIcon(item)
//        else -> FileIcon(item)
//    }
//}

@Composable
fun Icon(icon: ImageVector, contentDescription: String?, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    return when (onClick) {
        null -> Box(modifier)
        else -> Icon(icon, contentDescription, modifier.clickable { onClick() })
    }
}

//@Composable
//private fun FolderIcon(item: TreeItem) {
//    when {
//        item.file.isError -> Icon(Default.Warning, null)
//        item.children.isNotEmpty() -> Icon(Default.KeyboardArrowDown, null)
//        else -> Icon(Default.KeyboardArrowRight, null)
//    }
//}
//
//@Composable
//private fun FileIcon(item: TreeItem) {
//    when (item.file.name.substringAfterLast(".").lowercase()) {
//        "kt" -> Icon(Default.Code, null, tint = Color(0xFF3E86A0))
//        "xml" -> Icon(Default.Code, null, tint = Color(0xFFC19C5F))
//        "txt" -> Icon(Default.Description, null, tint = Color(0xFF87939A))
//        "md" -> Icon(Default.Description, null, tint = Color(0xFF87939A))
//        "gitignore" -> Icon(Default.BrokenImage, null, tint = Color(0xFF87939A))
//        "gradle" -> Icon(Default.Build, null, tint = Color(0xFF87939A))
//        "kts" -> Icon(Default.Build, null, tint = Color(0xFF3E86A0))
//        "properties" -> Icon(Default.Settings, null, tint = Color(0xFF62B543))
//        "bat" -> Icon(Default.Launch, null, tint = Color(0xFF87939A))
//        else -> Icon(Default.TextSnippet, null, tint = Color(0xFF87939A))
//    }
//}
