package com.example.twit.ui.screens.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.twit.R
import com.example.twit.model.TwitData
import com.example.twit.navigation.Route
import com.example.twit.ui.theme.TwitTheme
import com.example.twit.utils.BottomAppBarLogin


var viewmodel: MainViewModel? = null

@Composable
fun main(model: MainViewModel = viewModel(), navController: NavController) {
    viewmodel = model
    TwitTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomAppBarLogin(
                    navController = navController,
                    ruta = Route.MainScreen
                )
            },
            floatingActionButton = {
                
                FloatingActionButton(onClick = {model.showAddTwit(true)}) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription =null )
                    
                }
                 },
            content = {

                DialogAddTwit(model)
                Twit(it,model)
            }
        )
    }
}

@Composable
fun DialogAddTwit(model: MainViewModel){
    val mainui= model.uiState.collectAsState()
    if (mainui.value.showAddTwit){
        Dialog(onDismissRequest = { model.showAddTwit(false) },) {
            Column {
                OutlinedTextField(value =mainui.value.description , onValueChange = {model.textDescription(it)})
                Button(onClick = {
                    model.addTwit(mainui.value.description)
                    model.showAddTwit(false)
                }) {
                    Text(text = "enviar")

                }
            }
        }
    }
}


@Composable
fun Twit(paddingValues: PaddingValues,model: MainViewModel) {
    val stateui by model.uiState.collectAsState()
    val items = stateui.twits.collectAsState(initial = listOf())
    Column(modifier = Modifier.padding(8.dp)) {
        Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
            )
            Column {
                LazyColumn {
                    items(items = items.value, key = {item: TwitData -> item.id!! }){

                        Content(
                            it.description,
                            ""
                        )

                    }

                }
            }
        }
        Divider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun Content(description: String, id: String) {
    Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Aris")
        Text(text = "@AristiDevs", modifier = Modifier.padding(horizontal = 8.dp))
        Text(text = "4h")
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_dots),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
    }
    Text(text =description, modifier = Modifier.padding(8.dp))
    Card {
        Image(painter = painterResource(id = R.drawable.profile), contentDescription = null)
    }
    icons()

}

@Composable
fun icons() {
    val gameUiState by viewmodel!!.uiState.collectAsState()
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { viewmodel?.newComent() }) {
            Icon(
                painter =
                if (!gameUiState.commentsCliked) {
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
                if (!gameUiState.repliesCliked) {
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
                if (!gameUiState.likesCliked) {
                    painterResource(R.drawable.ic_like)
                } else {
                    painterResource(R.drawable.ic_like_filled)
                }, contentDescription = null,
                tint =
                if (!gameUiState.likesCliked) {
                    Color.Gray
                } else {
                    Color.Red
                }
            )
        }
        Text(text = gameUiState.likes.toString())
    }
}
