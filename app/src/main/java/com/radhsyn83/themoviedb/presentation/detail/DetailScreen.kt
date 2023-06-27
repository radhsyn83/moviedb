package com.radhsyn83.themoviedb.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.radhsyn83.themoviedb.presentation.Screen
import com.radhsyn83.themoviedb.presentation.detail.components.DetailContents
import com.radhsyn83.themoviedb.presentation.detail.components.Header

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        state.detail?.let { detail ->
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Header(
                        detail = detail,
                        onBackClick = { navController.popBackStack() },
                        onTrailerClick = { id ->
                            navController.navigate(Screen.TrailersScreen.route + "/$id")
                        })
                }
                item {
                    DetailContents(
                        detail = detail,
                        onReviewsClick = { id ->
                            navController.navigate(Screen.ReviewsScreen.route + "/$id")
                        })
                }
                item {
                    Text(text = detail?.overview ?: "", modifier = Modifier.padding(10.dp))
                }
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
