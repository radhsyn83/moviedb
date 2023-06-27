package com.radhsyn83.themoviedb.presentation.movies.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.radhsyn83.themoviedb.BuildConfig
import com.radhsyn83.themoviedb.R
import com.radhsyn83.themoviedb.domain.model.Movie

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItems(
    movie: Movie,
    onItemClick: (Movie) -> Unit
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(movie)
            },
    ) {
        Column {
            val img = "${BuildConfig.IMAGE_URL}${movie.posterPath}"
            GlideImage(
                model = img,
                contentDescription = movie.title,
                modifier = Modifier.aspectRatio(0.65f)
            )
            Text(
                text = "${movie.title}",
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
                    text = "${movie.voteAverage}",
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}