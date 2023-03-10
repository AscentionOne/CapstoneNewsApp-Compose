package com.kenchen.capstonenewsappcompose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kenchen.capstonenewsappcompose.R

val Montserrat = FontFamily(
    Font(R.font.montserrat_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = Montserrat,
    h1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
)
