package com.radhsyn83.themoviedb.presentation.reviews.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.radhsyn83.themoviedb.R
import com.radhsyn83.themoviedb.domain.model.Review
import com.radhsyn83.themoviedb.ui.theme.Typography

@Composable
fun ReviewItems(
    reviews: Review,
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = reviews.author,
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
                Text(text = (reviews.authorDetails.rating).toString())
            }
            Text(text = reviews.content, modifier = Modifier.padding(10.dp))
        }
    }
}