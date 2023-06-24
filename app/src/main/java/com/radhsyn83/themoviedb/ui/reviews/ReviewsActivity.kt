package com.radhsyn83.themoviedb.ui.reviews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.radhsyn83.themoviedb.R
import com.radhsyn83.themoviedb.data.models.ReviewsModel
import com.radhsyn83.themoviedb.ui.theme.Error
import com.radhsyn83.themoviedb.ui.theme.Loading
import com.radhsyn83.themoviedb.ui.theme.OnBottomReached
import com.radhsyn83.themoviedb.ui.theme.TheMovieDBTheme
import com.radhsyn83.themoviedb.ui.theme.Typography
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsActivity : ComponentActivity() {
    private val viewModel = viewModels<ReviewsViewModel>()

    var title = "Movies"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = intent.getStringExtra("title") ?: "Movies"
        id = intent.getIntExtra("id", 0)
        viewModel.value.load(id)

        setContent {
            TheMovieDBTheme() {
                val isLoading = viewModel.value.loading
                val reviews = viewModel.value.reviews
                val error = viewModel.value.error
                val canLoadMore = viewModel.value.canLoadMore

                if (id == 0) {
                    viewModel.value.setErrorMessage("Review tidak ditemukan")
                }

                Screen(isLoading = isLoading, reviews = reviews, error = error, loadMore = {
                    if (canLoadMore) viewModel.value.load(id, true)
                })
            }
        }
    }

}


@Composable
fun Screen(
    reviews: List<ReviewsModel.Result>,
    isLoading: Boolean,
    error: String, loadMore: () -> Unit
) {
    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, topBar = {
        TopAppBar(
            title = {
                Row {
                    val context = LocalContext.current as ReviewsActivity
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .clickable {
                                context.onBackPressedDispatcher.onBackPressed()
                            },
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back),
                        contentDescription = "back"
                    )
                    Text(text = "Reviews", Modifier.padding(start = 10.dp))
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
                Items(reviews, loadMore)
        }
    })
}

@Composable
fun Items(reviews: List<ReviewsModel.Result>, loadMore: () -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState, modifier = Modifier.padding(5.dp)) {
        items(count = reviews.size) { i ->
            Card(
                elevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        text = reviews[i].author,
                        modifier = Modifier.padding(10.dp),
                        style = Typography.titleLarge
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Image(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = "star",
                            modifier = Modifier
                                .height(14.dp)
                                .width(14.dp)
                                .padding(bottom = 4.dp, start = 10.dp)
                        )
                        Text(text = (reviews[i].authorDetails.rating).toString())
                    }
                    Text(text = reviews[i].content, modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
    // call the extension function
    listState.OnBottomReached(buffer = 2, loadMore = loadMore)
}