package com.kenchen.capstonenewsappcompose.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
* Source data class
* TODO: can potentially changed to use DTO
* */

@Entity
data class Source(
    @field:Json(name = "id") val id: String? = null,
    @PrimaryKey
    @field:Json(name = "name") val name: String,
    @field:Json(name = "description") val description: String? = null,
    @field:Json(name = "url") val url: String? = null,
    @field:Json(name = "category") val category: String? = null,
    @field:Json(name = "language") val language: String? = null,
    @field:Json(name = "country") val country: String? = null,
)
