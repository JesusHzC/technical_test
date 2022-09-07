package com.example.technicaltest.dao.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.technicaltest.domain.movie.Image

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(images: List<Image>)

    @Query("SELECT * FROM images")
    fun getAllImages(): List<Image>

    @Query("DELETE FROM images")
    fun deleteAllImages()

}