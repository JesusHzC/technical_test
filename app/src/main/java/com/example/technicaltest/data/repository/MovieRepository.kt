package com.example.technicaltest.data.repository

import com.example.technicaltest.domain.movie.Movie
import com.example.technicaltest.domain.util.Resource

interface MovieRepository {
    suspend fun getPopularMovies(): Resource<List<Movie>>
    suspend fun getTopRatedMovies(): Resource<List<Movie>>
    suspend fun getUpcomingMovies(): Resource<List<Movie>>
}