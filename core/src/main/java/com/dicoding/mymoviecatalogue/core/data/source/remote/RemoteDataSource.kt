package com.dicoding.mymoviecatalogue.core.data.source.remote

import android.annotation.SuppressLint
import com.dicoding.mymoviecatalogue.core.BuildConfig.API_KEY
import com.dicoding.mymoviecatalogue.core.data.source.remote.api.ApiResponse
import com.dicoding.mymoviecatalogue.core.data.source.remote.api.ApiService
import com.dicoding.mymoviecatalogue.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

@SuppressLint("CheckResult")
fun getMovieList(): Flow<ApiResponse<List<MovieItem>>> {
    return flow {
        try {
            val response = apiService.getMovies(API_KEY)
            val dataArray = response.results
            if(dataArray.isNotEmpty()){
                emit(ApiResponse.Success(response.results))
            }else{
                emit(ApiResponse.Empty)
            }
        }catch (e: Exception){
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)
    }

    @SuppressLint("CheckResult")
    fun getTvList(): Flow<ApiResponse<List<TvItem>>>{
        return flow {
            try {
                val response = apiService.getTVShows(API_KEY)
                val dataArray = response.results
                if(dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    }

    @SuppressLint("CheckResult")
    fun getMovieAddDetails(id: Int): Flow<ApiResponse<List<MovieDetailResponse>>>{
        return flow {
            try {
                val response = apiService.getDetailMovies(id,API_KEY)
                val dataArray = listOf(response)
                if(dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    @SuppressLint("CheckResult")
    fun getTvAddDetails(id: Int): Flow<ApiResponse<List<TvDetailResponse>>>{
        return flow {
            try {
                val response = apiService.getDetailTvShows(id,API_KEY)
                val dataArray = listOf(response)
                if(dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    }
}