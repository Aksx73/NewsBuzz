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
    val author: String?,

    @SerializedName("content")
    val content: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("publishedAt")
    val publishedAt: String?,

    @SerializedName("source")
    val source: Source?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("urlToImage")
    val urlToImage: String?
)
