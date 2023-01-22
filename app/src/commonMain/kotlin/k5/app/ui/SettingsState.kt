package k5.app.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

class SettingsState(
    fontStyle: TextUnit = 13.sp,
    lineLength: Int = 120
) {
    var fontSize by mutableStateOf(fontStyle)
    var lineLength by mutableStateOf(lineLength)
}

val LocalSettingsState = staticCompositionLocalOf { SettingsState() }
