package k5.app.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

@Composable
actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle) = Font("font/$res.ttf", weight, style)
