package com.radhsyn83.themoviedb.presentation.trailers

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.radhsyn83.themoviedb.R
import com.radhsyn83.themoviedb.presentation.trailers.components.TrailerItems
import com.radhsyn83.themoviedb.presentation.youtube.YoutubeActivity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TrailersScreen(
    navController: NavController,
    viewModel: TrailersViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            title = {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                            .clickable {
                                navController.popBackStack()
                            },
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back),
                        contentDescription = "back"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Trailers", style = TextStyle(color = Color.White))
                }
            },
        )
    }, content = { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val context = LocalContext.current
            val listState = rememberLazyStaggeredGridState()
            LazyVerticalStaggeredGrid(
                state = listState,
                columns = StaggeredGridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(4.dp),
                content = {
                    items(state.trailers) {
                        TrailerItems(
                            trailer = it,
                            onItemClick = { sc ->
                                val intent = Intent(context, YoutubeActivity::class.java)
                                intent.putExtra("youtubeId", sc)
                                context.startActivity(intent)
                            }
                        )
                    }
                })
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
    })
}

