package com.noxx.fragmentfeature.screens.resultApiFragment

import androidx.lifecycle.ViewModel
import com.noxx.navigator.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultApiFragmentViewModel @Inject constructor(
    navigator: Navigator
) : ViewModel(), Navigator by navigator
