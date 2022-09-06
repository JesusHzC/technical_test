package com.example.technicaltest.domain.util

data class GalleryState(
    val isLoading: Boolean = false,
    val gallery: List<String> = emptyList(),
    val message: String? = null
)