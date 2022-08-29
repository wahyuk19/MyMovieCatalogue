package com.dicoding.mymoviecatalogue.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvShow_detail_entities")
data class TvShowDetailEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val tvId: Int?,

    @ColumnInfo(name ="episode_run_time")
    val runtime: Int?,

    @ColumnInfo(name ="tagline")
    val tagline: String?
)