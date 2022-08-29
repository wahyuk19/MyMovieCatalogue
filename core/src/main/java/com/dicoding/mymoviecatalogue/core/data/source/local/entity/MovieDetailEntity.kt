package com.dicoding.mymoviecatalogue.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail_entities")
data class MovieDetailEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val movieId: Int?,

    @ColumnInfo(name = "runtime")
    val runtime: Int?,

    @ColumnInfo(name ="tagline")
    val tagline: String?
)