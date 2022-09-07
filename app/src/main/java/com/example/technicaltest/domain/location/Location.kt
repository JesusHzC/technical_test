package com.example.technicaltest.domain.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
    var latitude: Double? = null,
    var longitude: Double? = null,
    var date: String? = null,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
