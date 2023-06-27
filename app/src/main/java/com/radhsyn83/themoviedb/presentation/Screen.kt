package com.radhsyn83.themoviedb.presentation

sealed class Screen(val route: String) {
    object DetailScreen: Screen("DetailScreen")
    object MoviesScreen: Screen("MoviesScreen")
    object ReviewsScreen: Screen("ReviewsScreen")
    object TrailersScreen: Screen("TrailersScreen")
    object GenresScreen: Screen("GenresScreen")
    object YoutubeScreen: Screen("YoutubeScreen")
}