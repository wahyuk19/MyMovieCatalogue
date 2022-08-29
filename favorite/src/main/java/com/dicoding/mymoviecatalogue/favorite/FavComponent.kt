package com.dicoding.mymoviecatalogue.favorite

import android.content.Context
import com.dicoding.mymoviecatalogue.di.FavModuleDependencies
import com.dicoding.mymoviecatalogue.favorite.movie.FavoriteMovieFragment
import com.dicoding.mymoviecatalogue.favorite.tvshow.FavoriteTvFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavModuleDependencies::class])
interface FavComponent {

    fun inject(activity: FavoriteMovieFragment)
    fun inject(activity: FavoriteTvFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favModuleDependencies: FavModuleDependencies): Builder
        fun build(): FavComponent
    }

}