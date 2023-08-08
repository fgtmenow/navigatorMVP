package com.noxx.composefeature.screens.composeWithPrimitive

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.noxx.designsystem.theme.NavMVPTheme

@Composable
fun ComposePrimitiveScreen(
    viewModel: ComposePrimitiveScreenViewModel = hiltViewModel()
) {
    NavMVPTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Compose screen with primitive args")

            Text(text = "String arg1=${viewModel.arg1}")
            Text(text = "Long arg2=${viewModel.arg2}")
        }
    }
}
