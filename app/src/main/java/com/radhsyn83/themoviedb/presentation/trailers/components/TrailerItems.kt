package com.radhsyn83.themoviedb.presentation.trailers.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.radhsyn83.themoviedb.R
import com.radhsyn83.themoviedb.domain.model.Trailer

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TrailerItems(
    trailer: Trailer,
    onItemClick: (String) -> Unit
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(trailer.youtubeSource)
            },
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1.77f)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Box(Modifier.fillMaxSize()) {
                val thumbnail = "https://img.youtube.com/vi/${trailer.youtubeSource}/0.jpg"
                GlideImage(
                    model = thumbnail,
                    contentDescription = trailer.youtubeSource,
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