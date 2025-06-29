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

)