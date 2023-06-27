package com.radhsyn83.themoviedb.presentation.genres.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.radhsyn83.themoviedb.domain.model.Genre

@Composable
fun GenreItems(
    genre: Genre,
    onItemClick: (Genre) -> Unit
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
               onItemClick(genre)
            }
    ) {
        Text(text = "${genre.name}", modifier = Modifier.padding(10.dp))
    }
}