package com.example.technicaltest.domain.movie

import com.google.gson.annotations.SerializedName

data class ImagesRequest(
    @SerializedName("id"       ) var id       : Int?                = null,
    @SerializedName("profiles" ) var profiles : List<Image>    = arrayListOf()
)
