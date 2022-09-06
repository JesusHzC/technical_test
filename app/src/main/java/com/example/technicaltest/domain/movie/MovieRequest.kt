package com.example.technicaltest.domain.movie

import com.google.gson.annotations.SerializedName

data class MovieRequest(
    @SerializedName("page"          ) var page         : Int?               = null,
    @SerializedName("results"       ) var movies       : List<Movie>        = listOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
    @SerializedName("total_results" ) var totalResults : Int?               = null
)