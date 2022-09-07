package com.example.technicaltest.domain.movie

import androidx.room.*
import com.example.technicaltest.domain.util.MovieType
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "movie"
)
data class Movie(
    @SerializedName("adult"             ) var adult            : Boolean?       = null,
    @SerializedName("backdrop_path"     ) var backdropPath     : String?        = null,
    @PrimaryKey
    @SerializedName("id"                ) var id               : Int?           = null,
    @SerializedName("title"             ) var title            : String?        = null,
    @SerializedName("original_language" ) var originalLanguage : String?        = null,
    @SerializedName("original_title"    ) var originalTitle    : String?        = null,
    @SerializedName("overview"          ) var overview         : String?        = null,
    @SerializedName("poster_path"       ) var posterPath       : String?        = null,
    @SerializedName("media_type"        ) var mediaType        : String?        = null,
    @SerializedName("genre_ids"         ) var genreIds         : List<Int>      = listOf<Int>(),
    @SerializedName("popularity"        ) var popularity       : Double?        = null,
    @SerializedName("release_date"      ) var releaseDate      : String?        = null,
    @SerializedName("video"             ) var video            : Boolean?       = null,
    @SerializedName("vote_average"      ) var voteAverage      : Double?        = null,
    @SerializedName("vote_count"        ) var voteCount        : Int?           = null,
    @SerializedName("type"              ) var type             : MovieType?     = null,
    @SerializedName("people_id"         ) var peopleId         : Int?           = null,
)
