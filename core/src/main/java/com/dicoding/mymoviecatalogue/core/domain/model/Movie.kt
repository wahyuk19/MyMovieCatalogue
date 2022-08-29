package com.dicoding.mymoviecatalogue.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val movieId: Int,
    var title: String,
    var overview: String,
    var voteAverage: Double,
    var releaseDate: String,
    var posterPath: String,
    var backdropPath: String,
    var isFavorite: Boolean
):Parcelable
