package k5.app.ui

//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.hoverable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.interaction.collectIsHoveredAsState
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.LocalContentColor
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun TreeItemView(item: TreeItem, modifier: Modifier = Modifier) {
//    val interactions = remember(::MutableInteractionSource)
//    val active by interactions.collectIsHoveredAsState()
//    Row(modifier.clickable { item.open() }.fillMaxWidth().hoverable(interactions), verticalAlignment = Alignment.CenterVertically) {
//        Spacer(Modifier.width(24.dp * item.level))
//        Box(Modifier.size(24.dp).padding(4.dp)) { Icon(item) }
//        val color = if (active) LocalContentColor.current.copy(0.60f) else LocalContentColor.current
//        Text(item.name, color = color, fontSize = LocalSettingsState.current.fontSize, overflow = Ellipsis, maxLines = 1)
//    }
//}
