package com.dicoding.mymoviecatalogue.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowDetails(
    val tvId: Int? = null,
    val runtime: Int? = null,
    val tagline: String? = null
):Parcelable
