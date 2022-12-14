package com.example.technicaltest.domain.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "images")
data class Image(
    @SerializedName("aspect_ratio" ) var aspectRatio : Double? = null,
    @SerializedName("file_path"    ) var filePath    : String? = null,
    @SerializedName("height"       ) var height      : Int?    = null,
    @SerializedName("iso_639_1"    ) var iso6391     : String? = null,
    @SerializedName("vote_average" ) var voteAverage : Double? = null,
    @SerializedName("vote_count"   ) var voteCount   : Int?    = null,
    @SerializedName("width"        ) var width       : Int?    = null,
    @PrimaryKey(autoGenerate = true) var id          : Int?    = null
)
