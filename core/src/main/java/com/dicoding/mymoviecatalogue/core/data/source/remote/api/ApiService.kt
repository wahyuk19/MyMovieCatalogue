package com.dicoding.mymoviecatalogue.core.data.source.remote.api

import com.dicoding.mymoviecatalogue.core.data.source.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ):MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovies(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ):MovieDetailResponse

    @GET("tv/popular")
    suspend fun getTVShows(
        @Query("api_key") apiKey: String
    ):TVResponse

    @GET("tv/{tv_id}")
    suspend fun getDetailTvShows(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String
    ):TvDetailResponse
}