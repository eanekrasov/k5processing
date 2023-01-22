package k5.app.ui

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Enter
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction.Companion.Search

@Composable
fun SearchField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: () -> Unit = {}
) = TextField(
    value,
    onValueChanged,
    modifier.onKeyEvent { e -> (e.key == Enter).also { if (it) onSearch() } },
    singleLine = true,
    placeholder = { Text("Query...") },
    leadingIcon = { Icon(Default.Search, null) },
    keyboardOptions = KeyboardOptions(imeAction = Search),
    keyboardActions = KeyboardActions(onSearch = { onSearch() })
)
