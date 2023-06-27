package com.radhsyn83.themoviedb.presentation.detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.radhsyn83.themoviedb.BuildConfig
import com.radhsyn83.themoviedb.R
import com.radhsyn83.themoviedb.domain.model.Detail
import com.radhsyn83.themoviedb.ui.theme.Typography

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailContents(
    detail: Detail,
    onReviewsClick: (Int) -> Unit
) {
    val poster = "${BuildConfig.IMAGE_URL}${detail?.posterPath}"
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
                    onReviewsClick(detail.id ?: 0)
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
}