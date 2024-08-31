package com.example.ordermenu.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ordermenu.R

// Set of Material typography styles to start with
val RobotoSlab = FontFamily(
    Font(R.font.roboto_slab_regular),
    Font(R.font.roboto_slab_bold, FontWeight.Bold)
)
val DefaultTextStyle = TextStyle(
    fontFamily = RobotoSlab,
    fontWeight = FontWeight.Normal
)
val Typography = Typography(
    displayLarge = DefaultTextStyle.copy(),
    displayMedium = DefaultTextStyle.copy(),
    displaySmall = DefaultTextStyle.copy(),
    headlineLarge = DefaultTextStyle.copy(),
    headlineMedium = DefaultTextStyle.copy(),
    headlineSmall = DefaultTextStyle.copy(
        fontSize = 32.sp
    ),
    titleLarge = DefaultTextStyle.copy(
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold
    ),
    titleMedium = DefaultTextStyle.copy(
        fontSize = 32.sp
    ),
    titleSmall = DefaultTextStyle.copy(
        fontSize = 30.sp
    ),
    bodyLarge = DefaultTextStyle.copy(
        fontSize = 24.sp,
    ),
    bodyMedium = DefaultTextStyle.copy(
        fontSize = 22.sp,
    ),
    bodySmall = DefaultTextStyle.copy(
        fontSize = 20.sp
    ),
    labelLarge = DefaultTextStyle.copy(
        fontSize = 16.sp
    ),
    labelMedium = DefaultTextStyle.copy(
        fontSize = 14.sp
    ),
    labelSmall = DefaultTextStyle.copy(
        fontSize = 12.sp
    )
)