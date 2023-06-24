package com.radhsyn83.themoviedb.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.radhsyn83.themoviedb.data.models.GenresModel
import com.radhsyn83.themoviedb.ui.movies.MoviesActivity
import com.radhsyn83.themoviedb.ui.theme.Error
import com.radhsyn83.themoviedb.ui.theme.Loading
import com.radhsyn83.themoviedb.ui.theme.TheMovieDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDBTheme() {
                val viewModel = viewModels<MainViewModel>().value
                val isLoading = viewModel.loading
                val genres = viewModel.genres
                val error = viewModel.error
                val isRefresh = viewModel.isRefreshing
                val pullRefreshState =
                    rememberPullRefreshState(isRefresh, { viewModel.load() })
                MainScreen(
                    isLoading = isLoading,
                    genres = genres,
                    error = error,
                    isRefresh = isRefresh,
                    pullRefreshState = pullRefreshState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    genres: List<GenresModel.Result>,
    isLoading: Boolean,
    error: String,
    pullRefreshState: PullRefreshState,
    isRefresh: Boolean,
    ) {
    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, topBar = {
        TopAppBar(
            title = {
                Text(text = "The Movie DB")
            },
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            elevation = 10.dp
        )
    }, content = { padding ->

        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {


                if (isLoading)
                    Loading()
                else if (error.isNotEmpty())
                    Error(error)
                else
                    Items(genres = genres)
            }
            PullRefreshIndicator(isRefresh, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    })
}

@Composable
fun Items(genres: List<GenresModel.Result>) {
    val context = LocalContext.current
    LazyColumn(state = rememberLazyListState(), modifier = Modifier.padding(5.dp)) {
        items(count = genres.size) { i ->
            Card(
                elevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        val intent = Intent(context, MoviesActivity::class.java)
                        intent.putExtra("id", genres[i].id)
                        intent.putExtra("title", genres[i].name)
                        context.startActivity(intent)
                    }
            ) {
                Text(text = "${genres[i].name}", modifier = Modifier.padding(10.dp))
            }
        }
    }
}