package com.example.twit.ui.screens.main


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.twit.R
import com.example.twit.model.AnimeItem
import com.example.twit.navigation.InfoScreen
import com.example.twit.navigation.MainScreen
import com.example.twit.ui.theme.TwitTheme
import com.example.twit.utils.BottomAppBarLogin

var viewmodel: MainViewModel? = null


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Main(model: MainViewModel = hiltViewModel(), navController: NavController) {
    viewmodel = model
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<MainStateUI>(
        initialValue = MainStateUI.Loading,
        key1 = lifecycle,
        key2 = model
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            model.uiState.collect { value = it }
        }
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
                    is MainStateUI.Loading -> LoadingScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                    is MainStateUI.Success -> Twit(
                        it,
                        uistate = uiState,
                        navController = navController
                    )
                    is MainStateUI.Error -> ErrorScreen(modifier = Modifier.fillMaxSize())
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
        //LoadingIcon()
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Twit(paddingValues: PaddingValues, uistate: MainStateUI, navController: NavController) {
    when (uistate) {
        is MainStateUI.Success -> {
            val animes = uistate.animes
            LazyColumn(modifier = Modifier.padding(paddingValues).padding(8.dp)) {
                items(items = animes, key = { item: AnimeItem -> item.id }) {
                    Content(
                        it.title ?: "",
                        it.main_picture?.medium ?: "",
                            it.id,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun Content(description: String, id: String, id_anime: Int, navController: NavController) {

    Text(text = description, modifier = Modifier.padding(8.dp))
    if (id.isNotEmpty()) {
        Card (modifier = Modifier.clickable {
            navController.navigate(InfoScreen(id_anime))
        }){
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(id)
                    .build(),
                contentDescription = "anime",
                modifier = Modifier.fillMaxWidth(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentScale = ContentScale.Crop,
            )
        }
    }

    //icons()


    /*
    @Composable
    fun icons() {
        val gameUiState by viewmodel!!.uiState.collectAsState()
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { viewmodel?.newComent() }) {
                Icon(
                    painter =
                    if (!gameUiState.commentsClicked) {
                        painterResource(R.drawable.ic_chat)
                    } else {
                        painterResource(R.drawable.ic_chat_filled)
                    },
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
            Text(text = gameUiState.comments.toString())
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            IconButton(onClick = { viewmodel!!.changeReply() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_rt), contentDescription = null, tint =
                    if (!gameUiState.repliesClicked) {
                        Color.Gray
                    } else {
                        Color.Green
                    }
                )
            }
            Text(text = gameUiState.replies.toString())
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            IconButton(onClick = { viewmodel!!.changeLike() }) {
                Icon(
                    painter =
                    if (!gameUiState.likesClicked) {
                        painterResource(R.drawable.ic_like)
                    } else {
                        painterResource(R.drawable.ic_like_filled)
                    }, contentDescription = null,
                    tint =
                    if (!gameUiState.likesClicked) {
                        Color.Gray
                    } else {
                        Color.Red
                    }
                )
            }
            Text(text = gameUiState.likes.toString())
        }

     */
}
