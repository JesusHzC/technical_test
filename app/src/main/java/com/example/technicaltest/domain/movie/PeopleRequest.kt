package com.example.technicaltest.domain.movie

import com.google.gson.annotations.SerializedName

data class PeopleRequest(
    @SerializedName("page"          ) var page         : Int?               = null,
    @SerializedName("results"       ) var people       : List<People>       = listOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
    @SerializedName("total_results" ) var totalResults : Int?               = null
)
