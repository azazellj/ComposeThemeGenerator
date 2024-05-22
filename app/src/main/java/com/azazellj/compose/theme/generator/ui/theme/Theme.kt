package com.azazellj.compose.theme.generator.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ThemeColors.colorM3SysDarkPrimary,
    secondary = ThemeColors.colorM3SysDarkSecondary,
    tertiary = ThemeColors.colorM3SysDarkTertiary,
    background = ThemeColors.colorM3SysDarkBackground,
    surface = ThemeColors.colorM3SysDarkSurface,
    onPrimary = ThemeColors.colorM3SysDarkOnPrimary,
    onSecondary = ThemeColors.colorM3SysDarkOnSecondary,
    onTertiary = ThemeColors.colorM3SysDarkOnTertiary,
    onBackground = ThemeColors.colorM3SysDarkOnBackground,
    onSurface = ThemeColors.colorM3SysDarkOnSurface,
)

private val LightColorScheme = lightColorScheme(
    primary = ThemeColors.colorM3SysLightPrimary,
    secondary = ThemeColors.colorM3SysLightSecondary,
    tertiary = ThemeColors.colorM3SysLightTertiary,
    background = ThemeColors.colorM3SysLightBackground,
    surface = ThemeColors.colorM3SysLightSurface,
    onPrimary = ThemeColors.colorM3SysLightOnPrimary,
    onSecondary = ThemeColors.colorM3SysLightOnSecondary,
    onTertiary = ThemeColors.colorM3SysLightOnTertiary,
    onBackground = ThemeColors.colorM3SysLightOnBackground,
    onSurface = ThemeColors.colorM3SysLightOnSurface,
)

@Composable
fun ComposeThemeGeneratorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
