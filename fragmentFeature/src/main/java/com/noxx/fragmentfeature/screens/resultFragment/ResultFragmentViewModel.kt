package com.noxx.fragmentfeature.screens.resultFragment

import androidx.lifecycle.ViewModel
import com.noxx.navigator.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultFragmentViewModel @Inject constructor(
    navigator: Navigator
) : ViewModel(), Navigator by navigator
