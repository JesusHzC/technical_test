package com.example.technicaltest.domain.util

import com.example.technicaltest.domain.movie.Movie

data class MovieState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val type: MovieType? = null
)

enum class MovieType {
    POPULAR,
    TOP_RATED,
    UPCOMING,
    TRENDING
}