package com.example.technicaltest.data.repository.local

import com.example.technicaltest.domain.gallery.Gallery

interface LocalGalleyRepository {
    suspend fun getAllImages(): List<Gallery>
    suspend fun saveImages(gallery: List<Gallery>)
    suspend fun deleteAllImages()
    suspend fun saveImage(gallery: Gallery)
}