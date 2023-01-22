package k5.app.ui

import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.graphics.vector.ImageVector

enum class Side(val alignment: Alignment, val collapseIcon: ImageVector, val expandIcon: ImageVector) {
    Left(TopEnd, Default.ArrowBack, Default.ArrowForward),
    Right(TopStart, Default.ArrowForward, Default.ArrowBack)
}
