package com.example.technicaltest.data.repository.local

import com.example.technicaltest.domain.movie.Movie
import com.example.technicaltest.domain.util.MovieType

interface LocalMovieRepository {
    suspend fun getMovies(): List<Movie>
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun saveMovie(movie: Movie)
    suspend fun getByType(type: MovieType): List<Movie>
    suspend fun getByPeople(peopleId: Int): List<Movie>
    suspend fun deleteMovies()
    suspend fun deleteByType(type: MovieType)
    suspend fun deleteByPeople(peopleId: Int)
}