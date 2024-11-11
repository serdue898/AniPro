package com.example.anipro.ui.screens.modifyAnime


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.anipro.R
import com.example.anipro.navigation.InfoScreen
import com.example.anipro.ui.theme.TwitTheme
import com.example.anipro.utils.TopAppBarLogin
import java.time.Instant
import java.time.ZoneId

var viewmodel: ModifyViewModel? = null


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Modify(model: ModifyViewModel = hiltViewModel(), navController: NavController, idAnime: Int) {
    viewmodel = model
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<ModifyStateUI>(
        initialValue = ModifyStateUI.Loading,
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
            topBar = { TopAppBarLogin(navController = navController) },

            content = {
                when (uiState) {
                    is ModifyStateUI.Loading -> LoadingScreen(
                        modifier = Modifier.fillMaxSize()
                    )

                    is ModifyStateUI.Success -> Twit(
                        it,
                        uistate = uiState as ModifyStateUI.Success,
                        model = model,
                        navController = navController
                    )

                    is ModifyStateUI.Error -> ErrorScreen(modifier = Modifier.fillMaxSize())
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Twit(
    paddingValues: PaddingValues,
    uistate: ModifyStateUI.Success,
    model: ModifyViewModel,
    navController: NavController
) {
    val tittle = uistate.anime.title
    val id = uistate.anime.main_picture.medium
    Column(modifier = Modifier
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())) {
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)){
            Row (modifier = Modifier.fillMaxSize()){
                Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text(text = tittle, modifier = Modifier.padding(7.dp))
                }
                Card(modifier = Modifier
                    .clickable {
                        navController.navigate(InfoScreen(uistate.anime.id))
                    }) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(id)
                            .build(),
                        contentDescription = "anime",
                        modifier = Modifier.wrapContentSize(),
                        error = painterResource(id = R.drawable.ic_broken_image),
                        placeholder = painterResource(id = R.drawable.loading_img),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }



        val text = remember { mutableStateOf("") }

        var addType by remember { mutableIntStateOf(0) }
        var checkedNotification by remember { mutableStateOf(false) }
        Row {
            Checkbox(checked = checkedNotification, onCheckedChange = { checkedNotification = it})
            Text(text = "Notificar episodios nuevos", Modifier.align(Alignment.CenterVertically))
        }

        Text(text = "Agregar Anime", modifier = Modifier.padding(16.dp))

        Row {
            RadioButton(selected = (addType == 0), onClick = { addType = 0 })
            Text(text = "Episodios", Modifier.align(Alignment.CenterVertically))
            RadioButton(selected = (addType == 1), onClick = { addType = 1 })
            Text(text = "Fecha", Modifier.align(Alignment.CenterVertically))
        }
        if (addType == 1) {

            val state = rememberDatePickerState()

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                DatePicker(
                    state = state,
                    modifier = Modifier.wrapContentSize(),
                    title = null
                )
            }



            Button(
                onClick = {
                    val selectedDate = state.selectedDateMillis?.let { millis ->
                        Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                },
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.End)
            ) {
                Text(text = "Agregar")
            }
        } else {
            Column (modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally), horizontalAlignment = Alignment.CenterHorizontally) {
                TextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    label = { Text("Episodios") },
                    modifier = Modifier.padding(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.End)
                ) {
                    Text(text = "Agregar")
                }
            }

        }


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

