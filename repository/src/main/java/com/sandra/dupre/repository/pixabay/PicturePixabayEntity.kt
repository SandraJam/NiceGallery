package com.sandra.dupre.repository.pixabay

data class PixabayEntity(
        val totalHits: Int,
        val hits: List<PicturePixabayEntity>
)

data class PicturePixabayEntity(
        val id: Int,
        val previewURL: String
)