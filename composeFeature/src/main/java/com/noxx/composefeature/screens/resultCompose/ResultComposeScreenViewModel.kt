package com.noxx.composefeature.screens.resultCompose

import androidx.lifecycle.ViewModel
import com.noxx.navigator.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultComposeScreenViewModel @Inject constructor(
    navigator: Navigator
) : ViewModel(), Navigator by navigator
