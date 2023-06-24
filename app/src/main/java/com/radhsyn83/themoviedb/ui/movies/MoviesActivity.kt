@file:OptIn(ExperimentalMaterialApi::class)

package com.radhsyn83.themoviedb.ui.movies

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.radhsyn83.themoviedb.BuildConfig
import com.radhsyn83.themoviedb.R
import com.radhsyn83.themoviedb.data.models.MoviesModel
import com.radhsyn83.themoviedb.ui.detail.DetailActivity
import com.radhsyn83.themoviedb.ui.theme.Error
import com.radhsyn83.themoviedb.ui.theme.Loading
import com.radhsyn83.themoviedb.ui.theme.OnBottomReached
import com.radhsyn83.themoviedb.ui.theme.TheMovieDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : ComponentActivity() {
    var title = "Movies"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = viewModels<MoviesViewModel>().value

        title = intent.getStringExtra("title") ?: "Movies"
        id = intent.getIntExtra("id", 0)
        viewModel.load(id)

        setContent {
            TheMovieDBTheme() {
                val isLoading = viewModel.loading
                val movies = viewModel.movies
                val error = viewModel.error
                val canLoadMore = viewModel.canLoadMore
                val isRefresh = viewModel.isRefreshing
                val pullRefreshState =
                    rememberPullRefreshState(isRefresh, { viewModel.load(id) })

                if (id == 0) {
                    viewModel.setErrorMessage("Genre movie tidak ditemukan")
                }

                Screen(
                    title,
                    isLoading = isLoading,
                    movies = movies,
                    error = error,
                    isRefresh = isRefresh,
                    pullRefreshState = pullRefreshState,
                    loadMore = {
                        if (canLoadMore) viewModel.load(id, true)

                    })
            }
        }
    }

}

@Composable
fun Screen(
    title: String,
    movies: List<MoviesModel.Result>,
    isLoading: Boolean,
    error: String,
    loadMore: () -> Unit,
    pullRefreshState: PullRefreshState,
    isRefresh: Boolean,
) {
    val context = LocalContext.current as MoviesActivity
    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, topBar = {
        TopAppBar(
            title = {
                Row {
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .clickable {
                                context.onBackPressedDispatcher.onBackPressed()
                            },
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back),
                        contentDescription = "back"
                    )
                    Text(text = title, Modifier.padding(start = 10.dp))
                }
            },
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            elevation = 10.dp
        )
    }, content = { padding ->
        Box(Modifier.pullRefresh(pullRefreshState)) {
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {
                if (isLoading)
                    Loading()
                else if (error.isNotEmpty())
                    Error(error)
                else
                    Items(movies, loadMore)
            }
            PullRefreshIndicator(isRefresh, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    })
}

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class,
)
@Composable
fun Items(movies: List<MoviesModel.Result>, loadMore: () -> Unit) {
    val listState = rememberLazyStaggeredGridState()
    val context = LocalContext.current
    LazyVerticalStaggeredGrid(
        state = listState,
        columns = StaggeredGridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(4.dp),
        content = {
            items(count = movies.size) { i ->
                Card(
                    elevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra("id", movies[i].id)
                            context.startActivity(intent)
                        },
                ) {
                    Column {
                        val img = "${BuildConfig.IMAGE_URL}${movies[i].posterPath}"
                        GlideImage(
                            model = img,
                            contentDescription = movies[i].title,
                            modifier = Modifier.aspectRatio(0.65f)
                        )
                        Text(
                            text = "${movies[i].title}",
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        )
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = "star",
                                modifier = Modifier
                                    .height(14.dp)
                                    .width(14.dp)
                                    .padding(bottom = 4.dp)
                            )
                            Text(
                                text = "${movies[i].voteAverage}",
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    )
    // call the extension function
    listState.OnBottomReached(buffer = 2, loadMore = loadMore)
}