package com.example.twit.ui.screens.info

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.twit.R
import com.example.twit.navigation.InfoScreen
import com.example.twit.ui.theme.TwitTheme
import com.example.twit.ui.theme.md_theme_light_surfaceTint
import com.example.twit.utils.BottomAppBarLogin
import com.google.accompanist.flowlayout.FlowRow

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
                    ruta = InfoScreen(idAnime)
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(paddingValues)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Text(text = anime.title, modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    Color.Black, RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(anime.main_picture.medium)
                        .build(),
                    contentDescription = "anime",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(8.dp)
                        ),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    contentScale = ContentScale.Crop,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp)
            ) {

                Text(
                    text = "Fecha fin:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = anime.end_date,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = "Fecha inicio:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = anime.start_date,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = "Status:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = anime.status,
                    modifier = Modifier.padding(2.dp)
                )

                Text(
                    text = "Type: ${anime.media_type}",
                    modifier = Modifier.padding(2.dp)
                )


            }
        }
        FlowRow(
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.CenterHorizontally),
            mainAxisSpacing = 8.dp, // Espacio horizontal entre los ítems
            crossAxisSpacing = 4.dp, // Espacio vertical entre las filas
        ) {
            anime.genres.forEach {
                Text(
                    text = it.name,
                    color = md_theme_light_surfaceTint
                )
            }
        }
        CustomOutlinedBox(
            modifier = Modifier.padding(top=16.dp , start = 8.dp, end = 8.dp),
            cornerText = "Synopsis"
        ) {
        Column(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                .animateContentSize()
        ) {

                val expanded = remember { mutableStateOf(true) }
                val rotationAngle by animateFloatAsState(targetValue = if (!expanded.value) 180f else 0f,
                    label = ""
                )
                Text(
                    text = anime.synopsis,
                    modifier = Modifier,
                    maxLines = if (expanded.value) 3 else Int.MAX_VALUE
                )
                IconButton(onClick = { expanded.value = !expanded.value }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "down",
                        modifier = Modifier.rotate(rotationAngle)
                    )
                }
        }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun Content() {
//    CustomOutlinedBox(
//        modifier = Modifier.padding(16.dp),
//        cornerText = "Nombre"
//    )


}
@Composable
fun CustomOutlinedBox(
    modifier: Modifier = Modifier,
    borderColor: Color = Color.Black,
    borderWidth: Float = 1f,
    cornerText: String,
    cornerTextBackgroundColor: Color = Color.White,
    content: @Composable () -> Unit
) {

    Box(modifier = modifier) {
        // Main box with border
        Box(
            modifier = Modifier
                .border(
                    BorderStroke(borderWidth.dp, borderColor),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(top = 8.dp) // Adjust padding to create space for the label
                .padding(horizontal = 8.dp, vertical = 4.dp) // Content padding
        ) {
            // Content inside the box
            content()
        }
        // Label box
        Box(
            modifier = Modifier
                .padding(start = 8.dp) // Position the label
                .offset(y = (-8).dp)
                .background(cornerTextBackgroundColor)
                .zIndex(1f) // Ensure the label is drawn on top
        ) {
            Text(
                text = cornerText,
                fontSize = 12.sp,
                color = borderColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }

}
}

