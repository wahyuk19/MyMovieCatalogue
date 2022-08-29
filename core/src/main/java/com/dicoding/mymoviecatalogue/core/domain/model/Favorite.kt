package com.dicoding.mymoviecatalogue.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
    val id: Int?,
    val title: String?,
    val posterPath: String?,
    val type: String?
):Parcelable
