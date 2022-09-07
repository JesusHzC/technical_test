package com.example.technicaltest.dao.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.technicaltest.domain.movie.Movie
import com.example.technicaltest.domain.util.MovieType

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie WHERE type = :id")
    fun getByType(id: MovieType): List<Movie>

    @Query("SELECT * FROM movie WHERE peopleId = :id")
    fun getByPeople(id: Int): List<Movie>

    @Query("DELETE FROM movie")
    fun deleteAll()

    @Query("DELETE FROM movie WHERE type = :id")
    fun deleteByType(id: MovieType)

    @Query("DELETE FROM movie WHERE peopleId = :id")
    fun deleteByPeople(id: Int)

}