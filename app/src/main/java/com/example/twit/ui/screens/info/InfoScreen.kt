package com.example.twit.ui.screens.info

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.twit.R
import com.example.twit.model.AnimeItem
import com.example.twit.navigation.MainScreen
import com.example.twit.ui.theme.TwitTheme
import com.example.twit.utils.BottomAppBarLogin
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun Info(model: InfoViewModel = hiltViewModel(), navController: NavController, idAnime: Int) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<InfoStateUI>(
        initialValue = InfoStateUI.Loading,
        key1 = lifecycle,
        key2 = model
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            model.uiState.collect { value = it }
        }
    }
    LaunchedEffect(key1 = true) {
        model.getAnimeInfo(idAnime)
    }
    TwitTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomAppBarLogin(
                    navController = navController,
                    ruta = MainScreen
                )
            },
            content = {
                when (uiState) {
                    is InfoStateUI.Loading -> LoadingScreen(
                        modifier = Modifier.fillMaxSize()
                    )

                    is InfoStateUI.Success -> Anime(
                        it,
                        uiState = uiState
                    )

                    is InfoStateUI.Error -> ErrorScreen(modifier = Modifier.fillMaxSize())
                }

            }
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(text = "Algo Fallo")
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Anime(paddingValues: PaddingValues, uiState: InfoStateUI) {

    val anime = (uiState as InfoStateUI.Success).anime
    if (anime.main_picture.medium.isNotEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                Row {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(0.4f)
                    ) {
                        Text(text = anime.title, modifier = Modifier.padding(8.dp))
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(anime.main_picture.medium)
                                .build(),
                            contentDescription = "anime",
                            modifier = Modifier.size(100.dp),
                            error = painterResource(id = R.drawable.ic_broken_image),
                            placeholder = painterResource(id = R.drawable.loading_img),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Column {
                        Text(
                            text = "Fecha fin:${anime.end_date}",
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Fecha inicio:${anime.start_date}",
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Status:${anime.status}",
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Generos:",
                            modifier = Modifier.padding(4.dp)
                        )
                        Row {
                            for (genre in anime.genres) {
                                Text(
                                    text = "${genre.name}",
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                    }
                }
                Column {
                    val expanded = remember { mutableStateOf(true) }
                    Text(
                        text = "Sinopsis:${anime.synopsis}",
                        modifier = Modifier
                            .fillMaxHeight(0.4f),
                        maxLines = if (expanded.value) 5 else Int.MAX_VALUE
                    )
                    IconButton(onClick = { expanded.value = !expanded.value }) {
                        Icon(
                            imageVector = if (expanded.value)Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = "down"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Content() {
    Info( navController = NavController(LocalContext.current), idAnime = 1)


}
