package k5.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import k5.app.platform.Scrollbar

@Composable
fun ScrollColumn(
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit
) = Box(modifier) {
    LazyColumn(Modifier.fillMaxSize().unlimitedWidth(), scrollState) { content() }
    Scrollbar(Modifier.align(CenterEnd), scrollState)
}
