package com.noxx.composefeature.screens.composeWithPrimitive

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.noxx.navigator.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComposePrimitiveScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    navigator: Navigator
) : ViewModel(), Navigator by navigator {

    val arg1 = savedStateHandle.get<String>("ARG1")
    val arg2 = savedStateHandle.get<Long>("ARG2")
}
