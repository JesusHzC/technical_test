package com.example.technicaltest.domain.util

import com.example.technicaltest.domain.movie.Image
import com.example.technicaltest.domain.movie.People

data class PeopleState(
    val isLoading: Boolean = false,
    val person: People? = null,
    val message: String? = null,
    val images: List<Image>? = null,
)