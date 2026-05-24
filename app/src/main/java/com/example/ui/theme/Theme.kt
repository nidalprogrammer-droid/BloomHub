package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val StandardColorScheme =
  lightColorScheme(
    primary = SolidBlack,
    onPrimary = SolidWhite,
    secondary = SolidBlack,
    onSecondary = SolidWhite,
    background = PastelBackground,
    onBackground = SolidBlack,
    surface = SolidWhite,
    onSurface = SolidBlack,
    surfaceVariant = CardGray,
    onSurfaceVariant = SolidBlack,
  )

private val SunsetColorScheme =
  lightColorScheme(
    primary = SolidBlack,
    onPrimary = SolidWhite,
    secondary = SolidBlack,
    onSecondary = SolidWhite,
    background = WarmPastelBackground,
    onBackground = SolidBlack,
    surface = CardOrange,
    onSurface = SolidBlack,
    surfaceVariant = CardGray,
    onSurfaceVariant = SolidBlack,
  )

@Composable
fun MyApplicationTheme(
  isSunset: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (isSunset) SunsetColorScheme else StandardColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
