package com.example.technicaltest.dao.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.technicaltest.domain.gallery.Gallery

@Dao
interface GalleryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(gallery: List<Gallery>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gallery: Gallery)

    @Query("SELECT * FROM gallery")
    fun getAll(): List<Gallery>

    @Query("DELETE FROM gallery")
    fun deleteAll()

}