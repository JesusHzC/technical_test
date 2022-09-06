package com.example.technicaltest.data.remote

import com.example.technicaltest.domain.movie.MovieRequest
import retrofit2.http.GET

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieRequest

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieRequest

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MovieRequest

}