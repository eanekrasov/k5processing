@file:Suppress("SameParameterValue")

package k5.app.ui

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp

@Composable
fun StatusBar(modifier: Modifier = Modifier) {
    val settings = LocalSettingsState.current
    Box(modifier.height(32.dp).fillMaxWidth().padding(4.dp)) {
        Row(Modifier.fillMaxHeight().align(CenterEnd), spacedBy(8.dp), CenterVertically) {
            Text("Text size", color = LocalContentColor.current.copy(0.60f), fontSize = 12.sp)
            StatusBarSlider(settings.fontSize, 6.sp, 40.sp) { settings.fontSize = it }
            Text("Line length", color = LocalContentColor.current.copy(0.60f), fontSize = 12.sp)
            StatusBarSlider(settings.lineLength, 0, 240) { settings.lineLength = it }
        }
    }
}

@Composable
private fun StatusBarSlider(value: Int, min: Int, max: Int, onChange: (Int) -> Unit) {
    CompositionLocalProvider(LocalDensity provides LocalDensity.current * 0.5f) {
        Slider((value - min + 0f) / (max - min + 0f), { onChange(lerp(min, max, it)) }, Modifier.width(240.dp))
    }
}

@Composable
private fun StatusBarSlider(value: TextUnit, min: TextUnit, max: TextUnit, onChange: (TextUnit) -> Unit) {
    CompositionLocalProvider(LocalDensity provides LocalDensity.current * 0.5f) {
        Slider((value - min) / (max - min), { onChange(lerp(min, max, it)) }, Modifier.width(240.dp))
    }
}

internal operator fun Density.times(scale: Float) = Density(density * scale, fontScale * scale)
internal operator fun TextUnit.minus(other: TextUnit) = (value - other.value).sp
internal operator fun TextUnit.div(other: TextUnit) = value / other.value
