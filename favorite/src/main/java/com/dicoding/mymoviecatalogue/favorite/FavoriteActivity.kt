package com.dicoding.mymoviecatalogue.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mymoviecatalogue.favorite.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        val sectionsPagerAdapter = SectionsPagerFavoriteAdapter(this, supportFragmentManager)
        activityFavoriteBinding.viewPagerFavorite.adapter = sectionsPagerAdapter
        activityFavoriteBinding.tabsFavorite.setupWithViewPager(activityFavoriteBinding.viewPagerFavorite)

        supportActionBar?.elevation = 0f


    }
}