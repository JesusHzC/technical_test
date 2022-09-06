package com.example.technicaltest.data.remote

import com.example.technicaltest.domain.movie.ImagesRequest
import com.example.technicaltest.domain.movie.MovieRequest
import com.example.technicaltest.domain.movie.PeopleRequest
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    /** Movies */
    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieRequest

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieRequest

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MovieRequest

    /** Person */;
    @GET("person/popular")
    suspend fun getPopularPerson(): PeopleRequest

    @GET("person/{person_id}/images")
    suspend fun getPersonImages(
        @Path("person_id") personId: Int
    ): ImagesRequest

}