package com.noxx.composefeature.screens.startCompose

import androidx.lifecycle.ViewModel
import com.noxx.navigator.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartComposeScreenViewModel @Inject constructor(
    navigator: Navigator
) : ViewModel(), Navigator by navigator
