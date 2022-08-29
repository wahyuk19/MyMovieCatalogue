package com.dicoding.mymoviecatalogue.core.utils

import com.dicoding.mymoviecatalogue.core.data.source.local.entity.*
import com.dicoding.mymoviecatalogue.core.data.source.remote.response.*
import com.dicoding.mymoviecatalogue.core.domain.model.*

object DataMapper {
    fun mapResponseToEntitiesMovie(input: List<MovieItem>): List<MovieEntity>{
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                movieId = it.id,
                title = it.title,
                overview = it.overview,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapResponseToEntitiesTv(input: List<TvItem>): List<TvShowEntity>{
        val tvList = ArrayList<TvShowEntity>()
        input.map {
            val movie = TvShowEntity(
                tvId = it.id,
                name = it.name,
                overview = it.overview,
                voteAverage = it.voteAverage,
                releaseDate = it.firstAirDate,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                isFavorite = false
            )
            tvList.add(movie)
        }
        return tvList
    }

    fun mapResponseToEntitiesMovieDetail(input: List<MovieItem>) : List<MovieEntity>{
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                movieId = it.id,
                title = it.title,
                overview = it.overview,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                isFavorite = false
            )
        movieList.add(movie)
        }
        return movieList
    }

    fun mapResponseToEntitiesTvDetail(input: List<TvItem>) : List<TvShowEntity>{
        val tvList = ArrayList<TvShowEntity>()
        input.map {
            val movie = TvShowEntity(
                tvId = it.id,
                name = it.name,
                overview = it.overview,
                voteAverage = it.voteAverage,
                releaseDate = it.firstAirDate,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                isFavorite = false
            )
            tvList.add(movie)
        }
        return tvList
    }

    fun mapResponseToEntitiesExtMovie(input: List<MovieDetailResponse>) : List<MovieDetailEntity>{
        val movieList = ArrayList<MovieDetailEntity>()
        input.map {
            val movie = MovieDetailEntity(
                movieId = it.id,
                runtime = it.runtime,
                tagline = it.tagline
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapResponseToEntitiesExtTv(input: List<TvDetailResponse>) : List<TvShowDetailEntity>{
        val tvList = ArrayList<TvShowDetailEntity>()
        input.map {
            val tv = TvShowDetailEntity(
                tvId = it.id,
                runtime = it.runtime[0],
                tagline = it.tagline
            )
            tvList.add(tv)
        }
        return tvList
    }

    fun mapEntitiesToDomainMovie(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                movieId = it.movieId,
                title = it.title,
                overview = it.overview,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                isFavorite = it.isFavorite
            )
        }

    fun mapEntitiesToDomainTv(input: List<TvShowEntity>): List<TvShow> =
        input.map {
            TvShow(
                tvId = it.tvId,
                name = it.name,
                overview = it.overview,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath.toString(),
                isFavorite = it.isFavorite
            )
        }

    fun mapEntitiesToDomainMovieDetail(input: MovieEntity) = Movie(
        movieId = input.movieId,
        title = input.title,
        overview = input.overview,
        voteAverage = input.voteAverage,
        releaseDate = input.releaseDate,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        isFavorite = input.isFavorite
    )

    fun mapEntitiesToDomainTvDetail(input: TvShowEntity) = TvShow(
        tvId = input.tvId,
        name = input.name,
        overview = input.overview,
        voteAverage = input.voteAverage,
        releaseDate = input.releaseDate,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath.toString(),
        isFavorite = input.isFavorite
    )

    fun mapEntitiesToDomainExtMovie(input: MovieDetailEntity?) = MovieDetails(
        movieId = input?.movieId,
        runtime = input?.runtime,
        tagline = input?.tagline
    )

    fun mapEntitiesToDomainExtTv(input: TvShowDetailEntity?) = TvShowDetails(
        tvId = input?.tvId,
        runtime = input?.runtime,
        tagline = input?.tagline
    )

    fun mapDomainToEntityMovie(input: Movie) = MovieEntity(
        movieId = input.movieId,
        title = input.title,
        overview = input.overview,
        voteAverage = input.voteAverage,
        releaseDate = input.releaseDate,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        isFavorite = input.isFavorite
    )

    fun mapDomainToEntityTv(input: TvShow) = TvShowEntity(
        tvId = input.tvId,
        name = input.name,
        overview = input.overview,
        voteAverage = input.voteAverage,
        releaseDate = input.releaseDate,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        isFavorite = input.isFavorite
    )

    fun mapDomainToEntityFavorite(input: Favorite) = FavoriteEntity(
        id = input.id,
        title = input.title,
        posterPath = input.posterPath,
        type = input.type
    )
}