package k5.app.platform

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import java.awt.Cursor
import java.awt.Cursor.E_RESIZE_CURSOR

actual fun Modifier.cursorForHorizontalResize() = pointerHoverIcon(PointerIcon(Cursor(E_RESIZE_CURSOR)))
