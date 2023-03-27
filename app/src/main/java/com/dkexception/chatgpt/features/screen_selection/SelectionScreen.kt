@file:OptIn(ExperimentalMaterial3Api::class)

package com.dkexception.chatgpt.features.screen_selection

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dkexception.chatgpt.R

@Composable
fun SelectionScreen(
    onButtonAction: ((Int) -> Unit)? = null
) = Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
        CenterAlignedTopAppBar(
            title = { Text(stringResource(id = R.string.selection_screen_title)) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
) { paddingValues ->

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.inversePrimary
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ElevatedButton(
                onClick = { onButtonAction?.invoke(0) }
            ) {
                Text(text = "Chat")
            }

            Spacer(modifier = Modifier.height(32.dp))

            ElevatedButton(
                onClick = { onButtonAction?.invoke(1) }
            ) {
                Text(text = "Image generation")
            }
        }
    }
}
