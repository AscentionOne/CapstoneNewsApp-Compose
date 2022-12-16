package com.kenchen.capstonenewsappcompose.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kenchen.capstonenewsappcompose.database.converters.SourceConverter
import com.squareup.moshi.Json

/**
 * Article data class
 * TODO: can potentially changed to use DTO
 * */
@Entity
data class Article(
    @TypeConverters(SourceConverter::class)
    @field:Json(name = "source") val source: Source,
    @field:Json(name = "author") val author: String?,
    @PrimaryKey
    @field:Json(name = "title") val title: String,
    @field:Json(name = "description") val description: String? = null,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "urlToImage") val urlToImage: String? = null,
    @field:Json(name = "publishedAt") val publishedAt: String,
    @field:Json(name = "content") val content: String? = null,
)
