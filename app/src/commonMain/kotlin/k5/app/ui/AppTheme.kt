package k5.app.ui

import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import k5.app.platform.font

@Composable
fun AppTheme(
    colors: Colors = AppTheme.colors.material,
    settingsState: SettingsState = remember { SettingsState() },
    content: @Composable () -> Unit
) {
    MaterialTheme(colors) {
        CompositionLocalProvider(LocalSettingsState provides settingsState) {
            DisableSelection { content() }
        }
    }
}

object AppTheme {
    val colors = AppColors()

    val code = Code()

    class AppColors(
        val backgroundDark: Color = Color(0xFF2B2B2B),
        private val backgroundMedium: Color = Color(0xFF3C3F41),
        val backgroundLight: Color = Color(0xFF4E5254),

        val material: Colors = darkColors(
            background = backgroundDark,
            surface = backgroundMedium,
            primary = Color.White
        ),
    )

    class Code(
        val simple: SpanStyle = SpanStyle(Color(0xFFA9B7C6)),
        val value: SpanStyle = SpanStyle(Color(0xFF6897BB)),
        val keyword: SpanStyle = SpanStyle(Color(0xFFCC7832)),
        val punctuation: SpanStyle = SpanStyle(Color(0xFFA1C17E)),
        val annotation: SpanStyle = SpanStyle(Color(0xFFBBB529)),
        val comment: SpanStyle = SpanStyle(Color(0xFF808080))
    )

    val jetbrainsMono
        @Composable get() = FontFamily(
            font("JetBrains Mono", "jetbrainsmono_regular", FontWeight.Normal, FontStyle.Normal),
            font("JetBrains Mono", "jetbrainsmono_italic", FontWeight.Normal, FontStyle.Italic),
            font("JetBrains Mono", "jetbrainsmono_bold", FontWeight.Bold, FontStyle.Normal),
            font("JetBrains Mono", "jetbrainsmono_bold_italic", FontWeight.Bold, FontStyle.Italic),
            font("JetBrains Mono", "jetbrainsmono_extrabold", FontWeight.ExtraBold, FontStyle.Normal),
            font("JetBrains Mono", "jetbrainsmono_extrabold_italic", FontWeight.ExtraBold, FontStyle.Italic),
            font("JetBrains Mono", "jetbrainsmono_medium", FontWeight.Medium, FontStyle.Normal),
            font("JetBrains Mono", "jetbrainsmono_medium_italic", FontWeight.Medium, FontStyle.Italic)
        )
}
