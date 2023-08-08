package com.noxx.fragmentfeature.screens.fragment2

import androidx.lifecycle.ViewModel
import com.noxx.fragmentfeature.repo.DummyDep
import com.noxx.navigator.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeatureFragment2ViewModel @Inject constructor(
    dummyDep: DummyDep,
    navigator: Navigator
) : ViewModel(), Navigator by navigator {

    init {
        dummyDep.dummy()
    }
}
