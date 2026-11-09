package com.example.ui.util

import androidx.compose.ui.graphics.Color

fun parseColor(hex: String, fallback: Color = Color.White): Color {
    return try {
        val clean = hex.removePrefix("#")
        if (clean.length == 6 || clean.length == 8) {
            Color(android.graphics.Color.parseColor("#$clean"))
        } else {
            fallback
        }
    } catch (e: Exception) {
        fallback
    }
}
