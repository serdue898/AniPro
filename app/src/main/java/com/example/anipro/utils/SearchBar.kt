package com.example.anipro.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.anipro.R
import com.example.anipro.model.AnimeItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarAction(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: () -> Unit,
    list: List<AnimeItem>,
    modifier: Modifier = Modifier
) {
    SearchBar(
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = { onActiveChange() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(list) { anime ->
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                ) {

                    TextButton(onClick = {
                        onSearch(anime.title ?: "")
                    }) {
                        Text(
                            text = anime.title ?: "",
                            modifier = Modifier.padding(
                                start = 8.dp,
                                top = 4.dp,
                                end = 8.dp,
                                bottom = 4.dp
                            )
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = { onQueryChange(anime.title ?: "") },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_call_made_24),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}