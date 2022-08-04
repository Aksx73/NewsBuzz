package com.absut.newsapiclient.data.model


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)