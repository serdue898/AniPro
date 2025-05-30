package com.example.anipro.ui.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.anipro.R
import com.example.anipro.navigation.SettingsScreen
import com.example.anipro.ui.screens.modifyAnime.ModifyStateUI
import com.example.anipro.ui.theme.TwitTheme
import com.example.anipro.utils.BottomAppBarLogin

@Composable
fun Settings(settingsViewModel: SettingsViewModel = hiltViewModel(), navController: NavController) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<SettingsStateUI>(
        initialValue = SettingsStateUI.Loading,
        key1 = lifecycle,
        key2 = settingsViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            settingsViewModel.uiState.collect { value = it }
        }
    }
    LaunchedEffect(key1 = true) {
        settingsViewModel.getSettings()

    }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomAppBarLogin(navController = navController, ruta = SettingsScreen) },
            content = {
                when (uiState) {
                    is SettingsStateUI.Success -> {
                        SettingsMain(it,settingsViewModel, uiState as SettingsStateUI.Success)
                    }
                    is SettingsStateUI.Error -> {
                        Text(text = "Error")
                    }
                    is SettingsStateUI.Loading -> {
                        LoadingScreen()
                    }
                }

            }
        )



}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        //LoadingIcon()
    }
}

@Composable
fun SettingsMain(paddingValues: PaddingValues,model: SettingsViewModel, uiState: SettingsStateUI.Success) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        Text(text = "Settings", modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(16.dp))

        val theme = arrayOf("light", "dark", "default")
        SettingsType(text = "Dark Mode", icon = R.drawable.baseline_light_mode_24, options = theme, selected = theme.indexOf(uiState.theme), onClick = { model.saveTheme(it) })
        val colors = arrayOf("rojo", "azul", "morado")
        SettingsType(text = "Color theme", icon = R.drawable.baseline_format_paint_24, options = colors, selected = colors.indexOf(uiState.color), onClick = { model.saveColorTheme(it) })
        val grid = arrayOf("4x4", "5x5", "6x6")
        SettingsType(text = "grid type", icon = R.drawable.baseline_grid_4x4_24, options = grid, selected = grid.indexOf(uiState.grid), onClick = { model.saveGridType(it) })

    }
}


@Composable
fun SettingsType(modifier: Modifier = Modifier, text: String,  icon: Int , options: Array<String> = arrayOf("claro", "oscuro", "default"), selected: Int = 0, onClick: (String) -> Unit = {}) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(painter =  painterResource(id =icon ), contentDescription = "", modifier = Modifier.padding(16.dp))
        Text(text = text, modifier = Modifier.padding(16.dp))
        ExposedDropdownMenuBoxTheme(Modifier.align(Alignment.CenterVertically),options,selected,onClick = onClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxTheme(modifier: Modifier = Modifier , arratItems: Array<String> = arrayOf("claro", "oscuro", "default"),selected : Int = 0, onClick : (String) -> Unit = {}) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(arratItems[selected]) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors( unfocusedIndicatorColor = Color.Transparent, focusedIndicatorColor = Color.Transparent)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                arratItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onClick(item)
                            selectedText = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

