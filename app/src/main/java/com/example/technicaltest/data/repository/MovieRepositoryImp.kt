package com.example.technicaltest.data.repository

import com.example.technicaltest.data.remote.MovieApi
import com.example.technicaltest.domain.movie.Movie
import com.example.technicaltest.domain.util.Resource
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getPopularMovies(): Resource<List<Movie>> {
        return try {
            Resource.Success(
                data = api.getPopularMovies().movies
            )
        } catch (e: Exception) {
            Resource.Error(
                message = e.message ?: "Something went wrong"
            )
        }
    }

    override suspend fun getTopRatedMovies(): Resource<List<Movie>> {
        return try {
            Resource.Success(
                data = api.getTopRatedMovies().movies
            )
        } catch (e: Exception) {
            Resource.Error(
                message = e.message ?: "Something went wrong"
            )
        }
    }

    override suspend fun getUpcomingMovies(): Resource<List<Movie>> {
        return try {
            Resource.Success(
                data = api.getUpcomingMovies().movies
            )
        } catch (e: Exception) {
            Resource.Error(
                message = e.message ?: "Something went wrong"
            )
        }
    }

}