package k5.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Loading(modifier: Modifier = Modifier) = CircularProgressIndicator(modifier.size(36.dp).padding(4.dp))
