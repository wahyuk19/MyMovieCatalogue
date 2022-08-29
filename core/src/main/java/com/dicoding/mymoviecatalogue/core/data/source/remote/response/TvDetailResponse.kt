package com.dicoding.mymoviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvDetailResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("episode_run_time")
	val runtime: List<Int>,

	@field:SerializedName("tagline")
	val tagline: String,

)


