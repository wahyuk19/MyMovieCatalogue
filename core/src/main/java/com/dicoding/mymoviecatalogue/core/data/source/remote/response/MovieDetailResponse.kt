package com.dicoding.mymoviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("runtime")
	val runtime: Int? = null,

	@field:SerializedName("tagline")
	val tagline: String? = null

)

