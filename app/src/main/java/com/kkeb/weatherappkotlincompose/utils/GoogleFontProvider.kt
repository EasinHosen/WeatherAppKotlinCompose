package com.kkeb.weatherappkotlincompose.utils

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.kkeb.weatherappkotlincompose.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val font = GoogleFont("Rajdhani")

val appFontFamily = FontFamily(
    Font(googleFont = font, fontProvider = provider)
)