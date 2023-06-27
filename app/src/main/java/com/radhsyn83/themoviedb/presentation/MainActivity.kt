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

//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//    @OptIn(ExperimentalMaterialApi::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            TheMovieDBTheme() {
//                val viewModel = viewModels<MainViewModel>().value
//                val isLoading = viewModel.loading
//                val genres = viewModel.genres
//                val error = viewModel.error
//                val isRefresh = viewModel.isRefreshing
//                val pullRefreshState =
//                    rememberPullRefreshState(isRefresh, { viewModel.load() })
//                MainScreen(
//                    isLoading = isLoading,
//                    genres = genres,
//                    error = error,
//                    isRefresh = isRefresh,
//                    pullRefreshState = pullRefreshState
//                )
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun MainScreen(
//    genres: List<GenresDTO.Result>,
//    isLoading: Boolean,
//    error: String,
//    pullRefreshState: PullRefreshState,
//    isRefresh: Boolean,
//    ) {
//    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, topBar = {
//        TopAppBar(
//            title = {
//                Text(text = "The Movie DB")
//            },
//            backgroundColor = MaterialTheme.colorScheme.primary,
//            contentColor = Color.White,
//            elevation = 10.dp
//        )
//    }, content = { padding ->
//
//        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
//            Column(
//                modifier = Modifier
//                    .padding(padding)
//            ) {
//
//
//                if (isLoading)
//                    Loading()
//                else if (error.isNotEmpty())
//                    Error(error)
//                else
//                    Items(genres = genres)
//            }
//            PullRefreshIndicator(isRefresh, pullRefreshState, Modifier.align(Alignment.TopCenter))
//        }
//    })
//}
//
//@Composable
//fun Items(genres: List<GenresDTO.Result>) {
//    val context = LocalContext.current
//    LazyColumn(state = rememberLazyListState(), modifier = Modifier.padding(5.dp)) {
//        items(count = genres.size) { i ->
//            Card(
//                elevation = 2.dp,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(10.dp)
//                    .clickable {
//                        val intent = Intent(context, MoviesActivity::class.java)
//                        intent.putExtra("id", genres[i].id)
//                        intent.putExtra("title", genres[i].name)
//                        context.startActivity(intent)
//                    }
//            ) {
//                Text(text = "${genres[i].name}", modifier = Modifier.padding(10.dp))
//            }
//        }
//    }
//}