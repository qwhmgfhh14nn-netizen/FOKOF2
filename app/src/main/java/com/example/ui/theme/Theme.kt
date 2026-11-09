package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BarayaDarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = ObsidianBackground,
    primaryContainer = GoldDark,
    onPrimaryContainer = GoldLight,
    secondary = GoldSecondary,
    onSecondary = ObsidianBackground,
    secondaryContainer = ObsidianSurfaceElevated,
    onSecondaryContainer = GoldLight,
    tertiary = GoldTertiary,
    onTertiary = ObsidianBackground,
    background = ObsidianBackground,
    onBackground = TextLightHigh,
    surface = ObsidianSurface,
    onSurface = TextLightHigh,
    surfaceVariant = ObsidianSurfaceVariant,
    onSurfaceVariant = TextLightMedium,
    outline = ObsidianBorder,
    outlineVariant = GoldBorder,
    error = ErrorRuby,
    onError = Color.White
)

private val BarayaLightColorScheme = lightColorScheme(
    primary = Color(0xFFB89120),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFDF8E8),
    onPrimaryContainer = Color(0xFF8A680C),
    secondary = GoldPrimary,
    onSecondary = Color(0xFF141312),
    secondaryContainer = Color(0xFFF1F3F5),
    onSecondaryContainer = Color(0xFF141312),
    tertiary = GoldSecondary,
    onTertiary = Color.Black,
    background = Color(0xFFF8F9FA), // Elegant Light Gray
    onBackground = Color(0xFF1A1A1F),
    surface = Color(0xFFFFFFFF),    // Crisp Elegant White
    onSurface = Color(0xFF1A1A1F),
    surfaceVariant = Color(0xFFF1F3F5),
    onSurfaceVariant = Color(0xFF5A5A66),
    outline = Color(0xFFE2E4E8),
    outlineVariant = GoldBorder,
    error = ErrorRuby,
    onError = Color.White
)

@Composable
fun BarayaTheme(
    darkTheme: Boolean = false, // Elegant white and light gray default, optional dark theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) BarayaDarkColorScheme else BarayaLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = BarayaTypography,
        content = content
    )
}
