package com.example.technicaltest.dao.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.technicaltest.domain.movie.People

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(people: People)

    @Query("SELECT * FROM people")
    fun get(): People

    @Query("DELETE FROM people")
    fun deleteAll()

}