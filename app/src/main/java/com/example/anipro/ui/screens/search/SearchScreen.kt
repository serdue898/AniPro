package com.example.anipro.ui.screens.search


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.anipro.R
import com.example.anipro.model.AnimeItem
import com.example.anipro.navigation.InfoScreen
import com.example.anipro.navigation.SearchScreen
import com.example.anipro.ui.theme.TwitTheme
import com.example.anipro.utils.BottomAppBarLogin
import com.example.anipro.utils.SearchBarAction

var viewmodel: SearchViewModel? = null


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Search(model: SearchViewModel = hiltViewModel(), navController: NavController) {
    viewmodel = model
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<SearchStateUI>(
        initialValue = SearchStateUI.Loading,
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
            topBar = {
                if (uiState is SearchStateUI.Succes) {
                    SearchBarAction(
                        query = (uiState as SearchStateUI.Succes).search,
                        onQueryChange = { model.onSearchTextChange(it) },
                        onSearch = { model.onSearch(it) },
                        active = (uiState as SearchStateUI.Succes).isSearching,
                        onActiveChange = { model.onToogleSearch() },
                        list = (uiState as SearchStateUI.Succes).searchResult
                    )
                }
            },
            bottomBar = {
                BottomAppBarLogin(
                    navController = navController,
                    ruta = SearchScreen
                )
            },
            content = {
                when (uiState) {
                    is SearchStateUI.Loading -> LoadingScreen(
                        modifier = Modifier.fillMaxSize()
                    )

                    is SearchStateUI.Succes -> Twit(
                        it,
                        uistate = uiState,
                        navController = navController

                    )

                    is SearchStateUI.Error -> ErrorScreen(modifier = Modifier.fillMaxSize())
                }

            }
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(text = "something goes wrong")


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
fun Twit(paddingValues: PaddingValues, uistate: SearchStateUI, navController: NavController) {
    when (uistate) {
        is SearchStateUI.Succes -> {
            val animes = uistate.animes
            LazyVerticalGrid(
                columns = GridCells.Adaptive(128.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(8.dp)
            ) {
                items(items = animes, key = { item: AnimeItem -> item.id }) {
                    Content(
                        it.id,
                        it.title,
                        it.main_picture.medium,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun Content(id: Int, title: String, main_picture: String, navController: NavController) {
    Text(text = title, modifier = Modifier.padding(8.dp))
    if (main_picture.isNotEmpty()) {
        Card(modifier = Modifier.clickable {
            navController.navigate(InfoScreen(id))
        }) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(main_picture)
                    .build(),
                contentDescription = "anime picture medium",
                modifier = Modifier.fillMaxWidth(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentScale = ContentScale.Crop,
            )
        }
    }
}
