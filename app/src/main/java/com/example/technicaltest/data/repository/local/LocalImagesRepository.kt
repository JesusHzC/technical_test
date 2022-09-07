package com.example.technicaltest.data.repository.local

import com.example.technicaltest.domain.movie.Image

interface LocalImagesRepository {
    suspend fun getImages(): List<Image>
    suspend fun saveImages(images: List<Image>)
    suspend fun deleteImages()
}