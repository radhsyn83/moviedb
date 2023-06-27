package com.radhsyn83.themoviedb.presentation.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.radhsyn83.themoviedb.BuildConfig
import com.radhsyn83.themoviedb.R
import com.radhsyn83.themoviedb.domain.model.Detail
import com.radhsyn83.themoviedb.ui.theme.Typography

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Header(
    detail: Detail,
    onBackClick: () -> Unit,
    onTrailerClick: (Int) -> Unit,
) {
    val backdrop = "${BuildConfig.IMAGE_URL}${detail.backdropPath}"
    Box(
        modifier = Modifier
            .aspectRatio(1.77f)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Box(Modifier.fillMaxSize()) {
            GlideImage(
                model = backdrop,
                contentDescription = detail.originalName,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Box(
            Modifier
                .background(Color.Black.copy(alpha = 0.4f))
                .fillMaxSize()
        )
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
                        onBackClick()
                    },
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back),
                contentDescription = "back"
            )
        }
        Box(Modifier.align(Alignment.Center)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    onTrailerClick(detail.id ?: 0)
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
}