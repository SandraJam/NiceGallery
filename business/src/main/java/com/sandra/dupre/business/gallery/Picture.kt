package com.sandra.dupre.business.gallery

data class Picture(
        val id: Int,
        val previewUrl: String,
        val fullUrl: String
)

enum class Color {
    RED, ORANGE, YELLOW, GREEN, BLUE, LILAC, PINK, BROWN, NULL
}