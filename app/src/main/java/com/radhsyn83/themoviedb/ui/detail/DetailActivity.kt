package com.radhsyn83.themoviedb.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.radhsyn83.themoviedb.BuildConfig
import com.radhsyn83.themoviedb.R
import com.radhsyn83.themoviedb.data.models.DetailModel
import com.radhsyn83.themoviedb.ui.reviews.ReviewsActivity
import com.radhsyn83.themoviedb.ui.theme.Error
import com.radhsyn83.themoviedb.ui.theme.Loading
import com.radhsyn83.themoviedb.ui.theme.TheMovieDBTheme
import com.radhsyn83.themoviedb.ui.theme.Typography
import com.radhsyn83.themoviedb.ui.trailers.TrailersActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {
    private val viewModel = viewModels<DetailViewModel>()

    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.getIntExtra("id", 0)

        viewModel.value.load(id)

        setContent {
            TheMovieDBTheme() {
                val isLoading = viewModel.value.loading
                val detail = viewModel.value.detail
                val error = viewModel.value.error

                Screen(isLoading = isLoading, detail = detail, error = error)
            }
        }
    }

}

@Composable
fun Screen(
    detail: DetailModel?,
    isLoading: Boolean,
    error: String,
) {
    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, content = { padding ->
        if (isLoading)
            Loading()
        else if (error.isNotEmpty())
            Box(Modifier.padding(padding)) { Error(error) }
        else
            Detail(detail)
    })
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Detail(detail: DetailModel?) {
    val context = LocalContext.current as DetailActivity
    val backdrop = "${BuildConfig.IMAGE_URL}${detail?.backdropPath}"
    val poster = "${BuildConfig.IMAGE_URL}${detail?.posterPath}"
    Column {
        Box(
            modifier = Modifier
                .aspectRatio(1.77f)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Box(Modifier.fillMaxSize()) {
                GlideImage(
                    model = backdrop,
                    contentDescription = detail?.originalName,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Box(
                Modifier
                    .background(Color.Black.copy(alpha = 0.4f))
                    .fillMaxSize())
            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .padding(20.dp)
                    .height(50.dp)
                    .width(50.dp)
            ) {
                Image(
                    modifier = Modifier
                        .height(40.dp)
                        .clickable {
                            context.onBackPressedDispatcher.onBackPressed()
                        },
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back),
                    contentDescription = "back"
                )
            }
            Box(Modifier.align(Alignment.Center)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        val intent = Intent(context, TrailersActivity::class.java)
                        intent.putExtra("id", detail?.id)
                        context.startActivity(intent)
                    }) {
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(bottom = 8.dp),
                        painter = painterResource(id = R.drawable.play_circle),
                        contentDescription = "play"
                    )
                    Text(
                        text = "Trailer",
                        style = Typography.titleLarge,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
        Row(
            Modifier
                .padding(10.dp)
                .height(170.dp)
                .fillMaxWidth()
        ) {
            GlideImage(
                model = poster,
                contentDescription = detail?.originalName,
                modifier = Modifier
                    .aspectRatio(0.65f)
                    .fillMaxSize()
            )
            Column(Modifier.padding(start = 10.dp)) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = detail?.originalName ?: "",
                    style = Typography.titleLarge,
                    color = Color.Black
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "star",
                        modifier = Modifier
                            .height(14.dp)
                            .width(14.dp)
                            .padding(bottom = 4.dp)
                    )
                    Text(text = (detail?.voteAverage ?: 0.0).toString())
                }
                Text(text = detail?.status ?: "", modifier = Modifier.padding(vertical = 10.dp))
                OutlinedButton(
                    onClick = {
                        val intent = Intent(context, ReviewsActivity::class.java)
                        intent.putExtra("id", detail?.id)
                        context.startActivity(intent)
                    },
                    border = BorderStroke(1.dp, Color.Blue),
                    shape = RoundedCornerShape(20), // = 50% percent
                    // or shape = CircleShape
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)
                ) {
                    Text(text = "See Reviews")
                }
            }
        }
        Text(text = detail?.overview ?: "", modifier = Modifier.padding(10.dp))
    }
}
