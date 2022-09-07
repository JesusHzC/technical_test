package com.example.technicaltest.data.repository.local

import com.example.technicaltest.dao.DBTest
import com.example.technicaltest.domain.movie.Movie
import com.example.technicaltest.domain.util.MovieType
import javax.inject.Inject

class LocalMovieRepositoryImp @Inject constructor(
    private val dbTest: DBTest
) : LocalMovieRepository {

    override suspend fun getMovies(): List<Movie> {
        return dbTest.getMovieDao().getAll()
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        dbTest.getMovieDao().insertAll(movies)
    }

    override suspend fun saveMovie(movie: Movie) {
        dbTest.getMovieDao().insert(movie)
    }

    override suspend fun getByType(type: MovieType): List<Movie> {
        return dbTest.getMovieDao().getByType(type)
    }

    override suspend fun getByPeople(peopleId: Int): List<Movie> {
        return dbTest.getMovieDao().getByPeople(peopleId)
    }

    override suspend fun deleteMovies() {
        dbTest.getMovieDao().deleteAll()
    }

    override suspend fun deleteByType(type: MovieType) {
        dbTest.getMovieDao().deleteByType(type)
    }

    override suspend fun deleteByPeople(peopleId: Int) {
        dbTest.getMovieDao().deleteByPeople(peopleId)
    }

}