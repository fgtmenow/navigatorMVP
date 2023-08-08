package com.noxx.composefeature.screens.startCompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.noxx.designsystem.theme.NavMVPTheme
import com.noxx.navigation.destinations.compose.ComposeWithPrimitiveDestination
import com.noxx.navigation.destinations.compose.ResultComposeDestination
import com.noxx.navigation.destinations.compose.ResultComposeDestination.REQUEST_COMPOSE_KEY
import com.noxx.navigation.destinations.fragment.Fragment2Destination
import com.noxx.navigation.destinations.fragment.FragmentSaveStateDestination
import com.noxx.navigation.destinations.fragment.FragmentWithParcelableDestination
import com.noxx.navigation.destinations.fragment.FragmentWithPrimitiveDestination
import com.noxx.navigation.destinations.fragment.ResultFragmentDestination
import com.noxx.navigation.destinations.fragment.ResultFragmentDestination.REQUEST_FRAGMENT_KEY

@Composable
fun StartComposeScreen(viewModel: StartComposeScreenViewModel = hiltViewModel()) {
    NavMVPTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Start compose screen",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Navigation")
            Button(onClick = {
                viewModel.navigate(ComposeWithPrimitiveDestination.createRoute("test", 69L))
            }) {
                Text(text = "Compose to compose with primitive")
            }
            Button(onClick = {
                viewModel.navigate(FragmentWithPrimitiveDestination.createRoute("test", 69L))
            }) {
                Text(text = "Compose to fragment with primitive")
            }
            Button(onClick = {
                viewModel.navigate(
                    FragmentWithParcelableDestination.createRoute(
                        arg1 = "test",
                        arg2 = 69L,
                        parcel = FragmentWithParcelableDestination.DataParcelableTestArgument("parcel")
                    )
                )
            }) {
                Text(text = "Compose to fragment with parcelable")
            }
            Spacer(modifier = Modifier.height(24.dp))

            //
            Text(text = "Result api")
            Button(onClick = {
                viewModel.navigate(ResultFragmentDestination.createRoute())
            }) {
                Text(
                    text = "fragment to compose result api Result=${
                    viewModel.getNavigationResult<String>(
                        REQUEST_FRAGMENT_KEY
                    )
                    }"
                )
            }
            Button(onClick = {
                viewModel.navigate(ResultComposeDestination.createRoute())
            }) {
                Text(
                    text = "compose to compose result api Result=${
                    viewModel.getNavigationResult<String>(
                        REQUEST_COMPOSE_KEY
                    )
                    }"
                )
            }
            Button(onClick = {
                viewModel.navigate(Fragment2Destination.createRoute())
            }) {
                Text(
                    text = "fragment to fragment result api"
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            //

            Text(text = "others")
            Button(onClick = {
                viewModel.navigate(FragmentSaveStateDestination.createRoute("ggwp", 11))
            }) {
                Text(
                    text = "Fragment saving state"
                )
            }
            Button(onClick = {
                viewModel.navigate("app/sheet")
            }) {
                Text(
                    text = "bottom sheet title"
                )
            }
        }
    }
}
