package com.example.technicaltest.domain.gallery

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gallery")
data class Gallery(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var url: String = "",
)
