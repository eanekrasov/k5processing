package k5.app.ui

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import k5.app.ui.AppTheme.jetbrainsMono

@Composable
internal fun ScaledText(text: String, modifier: Modifier = Modifier) {
    Row(modifier.height(1.6f), spacedBy(8.dp)) {
        Text(text, fontSize = LocalSettingsState.current.fontSize, fontFamily = jetbrainsMono, softWrap = false)
    }
}

@Composable
internal fun ScaledText(text: AnnotatedString, modifier: Modifier = Modifier) {
    Row(modifier.height(1.6f), spacedBy(8.dp)) {
        Text(text, fontSize = LocalSettingsState.current.fontSize, fontFamily = jetbrainsMono, softWrap = false)
    }
}
