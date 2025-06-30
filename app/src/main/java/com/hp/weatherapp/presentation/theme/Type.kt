package com.hp.weatherapp.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hp.weatherapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        color = Yellow,
        fontSize = 22.sp,
        fontFamily = FontFamily(Font(R.font.poppins_medium))
    ),
    titleMedium = TextStyle(
        color = White,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.poppins_medium))
    ),
    titleSmall = TextStyle(
        color = White,
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.poppins_medium))
    ),
    bodySmall=TextStyle(
        color = White,
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.poppins_regular))
    ),
    bodyMedium = TextStyle(
        color = White,
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.poppins_regular))
    ),
    bodyLarge = TextStyle(
        color = Purple,
        fontSize = 64.sp,
        fontFamily = FontFamily(Font(R.font.poppins_semibold))
    ),
    displayLarge = TextStyle(
        color = Purple.copy(.7f),
        fontSize = 28.sp,
        fontFamily = FontFamily(Font(R.font.poppins_medium))
    ),
    displayMedium = TextStyle(
        color = Purple.copy(.8f),
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(R.font.poppins_medium))
    ),
    displaySmall = TextStyle(
        color = Purple.copy(.8f),
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.poppins_regular))
    ),
    labelSmall = TextStyle(
        color = Purple.copy(.8f),
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(R.font.poppins_regular))
    ),

)