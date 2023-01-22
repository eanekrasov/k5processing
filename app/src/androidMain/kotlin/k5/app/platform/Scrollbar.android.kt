package com.sample.viewer.platform

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.ScrollState

@Composable
actual fun Scrollbar(modifier: Modifier, scrollState: ScrollState) = Unit

@Composable
actual fun Scrollbar(modifier: Modifier, scrollState: LazyListState) = Unit
