package com.noxx.composefeature.screens.resultCompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.noxx.designsystem.theme.NavMVPTheme
import com.noxx.navigation.destinations.compose.ResultComposeDestination.REQUEST_COMPOSE_KEY

@Composable
fun ResultComposeScreen(viewModel: ResultComposeScreenViewModel = hiltViewModel()) {
    NavMVPTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Button(onClick = {
                viewModel.navigateUp()
            }) {
                Text(text = "go back")
                viewModel.setNavigationResult(
                    key = REQUEST_COMPOSE_KEY,
                    value = "This is compose result"
                )
            }
        }
    }
}
