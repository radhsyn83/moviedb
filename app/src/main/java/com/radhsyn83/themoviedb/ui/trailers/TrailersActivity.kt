package com.radhsyn83.themoviedb.ui.trailers

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.radhsyn83.themoviedb.R
import com.radhsyn83.themoviedb.data.models.TrailersModel
import com.radhsyn83.themoviedb.ui.YoutubeActivity
import com.radhsyn83.themoviedb.ui.theme.Error
import com.radhsyn83.themoviedb.ui.theme.Loading
import com.radhsyn83.themoviedb.ui.theme.TheMovieDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrailersActivity : ComponentActivity() {
    private val viewModel = viewModels<TrailersViewModel>()

    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = intent.getStringExtra("title") ?: "Movies"
        id = intent.getIntExtra("id", 0)
        viewModel.value.load(id)

        setContent {
            TheMovieDBTheme() {
                val isLoading = viewModel.value.loading
                val trailers = viewModel.value.trailers
                val error = viewModel.value.error

                if (id == 0) {
                    viewModel.value.setErrorMessage("Genre movie tidak ditemukan")
                }

                Screen(isLoading = isLoading, trailers = trailers, error = error)
            }
        }
    }

}

@Composable
fun Screen(
    trailers: List<TrailersModel.Youtube>,
    isLoading: Boolean,
    error: String,
) {
    val context = LocalContext.current as TrailersActivity
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
                    Text(text = "Trailers", Modifier.padding(start = 10.dp))
                }
            },
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            elevation = 10.dp
        )
    }, content = { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            if (isLoading)
                Loading()
            else if (error.isNotEmpty())
                Error(error)
            else
                com.radhsyn83.themoviedb.ui.trailers.Items(trailers = trailers)
        }
    })
}

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun Items(trailers: List<TrailersModel.Youtube>) {
    val context = LocalContext.current
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(4.dp),
        content = {
            items(count = trailers.size) { i ->
                Card(
                    elevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(context, YoutubeActivity::class.java)
                            intent.putExtra("youtubeId", trailers[i].source)
                            context.startActivity(intent)
                        },
                ) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1.77f)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Box(Modifier.fillMaxSize()) {
                            val thumbnail = "https://img.youtube.com/vi/${trailers[i].source}/0.jpg"
                            GlideImage(
                                model = thumbnail,
                                contentDescription = trailers[i].source,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                        }
                        Box(
                            Modifier
                                .background(Color.Black.copy(alpha = 0.4f))
                                .fillMaxSize()
                        )
                        Box(Modifier.align(Alignment.Center)) {
                            Image(
                                modifier = Modifier
                                    .height(40.dp)
                                    .padding(bottom = 8.dp),
                                painter = painterResource(id = R.drawable.play_circle),
                                contentDescription = "play"
                            )
                        }
                    }
                }
            }
        }
    )
}