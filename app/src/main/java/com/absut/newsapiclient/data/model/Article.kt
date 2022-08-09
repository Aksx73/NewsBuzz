package com.absut.newsapiclient.data.model


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles", indices = [Index(value = ["title", "url"], unique = true)])
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("content")
    val content: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("publishedAt")
    val publishedAt: String? = null,

    @SerializedName("source")
    val source: Source?,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("urlToImage")
    val urlToImage: String? = null
)
