package com.example.technicaltest.domain.movie

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "people")
data class People(
    @SerializedName("adult"                ) var adult              : Boolean?            = null,
    @SerializedName("gender"               ) var gender             : Int?                = null,
    @PrimaryKey
    @SerializedName("id"                   ) var id                 : Int?                = null,
    @Ignore
    @SerializedName("known_for"            ) var knownFor           : ArrayList<Movie>    = arrayListOf(),
    @SerializedName("known_for_department" ) var knownForDepartment : String?             = null,
    @SerializedName("name"                 ) var name               : String?             = null,
    @SerializedName("popularity"           ) var popularity         : Double?             = null,
    @SerializedName("profile_path"         ) var profilePath        : String?             = null
)
