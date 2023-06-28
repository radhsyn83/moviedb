package com.radhsyn83.themoviedb.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.radhsyn83.themoviedb.presentation.detail.DetailScreen
import com.radhsyn83.themoviedb.presentation.genres.GenresScreen
import com.radhsyn83.themoviedb.presentation.movies.MoviesScreen
import com.radhsyn83.themoviedb.presentation.reviews.ReviewsScreen
import com.radhsyn83.themoviedb.presentation.trailers.TrailersScreen
import com.radhsyn83.themoviedb.presentation.youtube.YoutubeScreen
import com.radhsyn83.themoviedb.ui.theme.TheMovieDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDBTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.GenresScreen.route
                    ) {
                        composable(
                            route = Screen.GenresScreen.route
                        ) {
                            GenresScreen(navController)
                        }
                        composable(
                            route = Screen.MoviesScreen.route + "/{id}/{title}"
                        ) {
                            MoviesScreen(navController)
                        }
                        composable(
                            route = Screen.DetailScreen.route + "/{id}"
                        ) {
                            DetailScreen(navController)
                        }
                        composable(
                            route = Screen.ReviewsScreen.route + "/{id}"
                        ) {
                            ReviewsScreen(navController)
                        }
                        composable(
                            route = Screen.TrailersScreen.route + "/{id}"
                        ) {
                            TrailersScreen(navController)
                        }
                        composable(
                            route = Screen.YoutubeScreen.route + "/{id}"
                        ) {
                            YoutubeScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
