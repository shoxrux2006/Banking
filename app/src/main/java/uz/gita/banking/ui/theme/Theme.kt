package uz.gita.banking.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import uz.gita.banking.data.`var`.TextStyle
import uz.gita.banking.data.`var`.ThemeType

object ApplicatiomTheme {
    var themeType = mutableStateOf(ThemeType.Custom1)
    var textStyle = mutableStateOf(TextStyle.Custom1)
}


private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    secondary = PurpleGrey80,
    tertiary = Color.White,
    background = Color.Black,
    surface = surfaceDark
)


private val Custom = lightColorScheme(
    primary = Color.Red,
    secondary = PurpleGrey80,
    tertiary = Color.Blue,
    background = Color.Cyan,
    surface = surfaceDark
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    secondary = PurpleGrey40,
    tertiary = Color.Black,
    background = Color.White,
    surface = surfaceLight
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


@Composable
fun BankingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, content: @Composable () -> Unit
) {
    val colorScheme = when (ApplicatiomTheme.themeType.value) {
        ThemeType.Custom1 -> {
            if (darkTheme) DarkColorScheme
            else LightColorScheme
        }
        ThemeType.Custom2 -> {
            Custom
        }
        ThemeType.Custom3 -> {
            Custom
        }
    }

    val typography = when (ApplicatiomTheme.textStyle.value) {
        TextStyle.Custom1 -> Typography
        TextStyle.Custom2 -> Typography1
        TextStyle.Custom3 -> Typography
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = typography, content = content
    )

}