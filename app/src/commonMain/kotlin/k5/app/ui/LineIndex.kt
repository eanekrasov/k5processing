package k5.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import k5.app.ui.AppTheme.jetbrainsMono

@Composable
internal fun LineIndex(
    index: Int,
    max: String,
    color: Color = LocalContentColor.current.copy(0.30f)
) = DisableSelection {
    Box(Modifier.padding(start = 12.dp), contentAlignment = CenterEnd) {
        Text(max, Modifier.alpha(0f), color, LocalSettingsState.current.fontSize, fontFamily = jetbrainsMono)
        Text(index.toString(), Modifier, color, LocalSettingsState.current.fontSize, fontFamily = jetbrainsMono)
    }
}
