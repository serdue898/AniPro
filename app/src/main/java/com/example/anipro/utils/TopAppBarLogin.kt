package com.example.anipro.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarLogin(navController: NavController) {
    TopAppBar(title = { Text(text = "AniPro")},
        navigationIcon = {
           IconButton(onClick = { navController.popBackStack() }) {
               Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Menu")
           }
        })
}

