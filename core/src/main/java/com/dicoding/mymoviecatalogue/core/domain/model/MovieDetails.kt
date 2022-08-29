package com.dicoding.mymoviecatalogue.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetails(
    val movieId: Int? = null,
    val runtime: Int? = null,
    val tagline: String? = null
):Parcelable
