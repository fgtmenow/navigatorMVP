package com.noxx.fragmentfeature.screens.fragmentWithParcelable

import androidx.lifecycle.ViewModel
import com.noxx.fragmentfeature.repo.DummyDep
import com.noxx.navigator.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentWithParcelableViewModel @Inject constructor(
    dummyDep: DummyDep,
    navigator: Navigator
) : ViewModel(), Navigator by navigator {

    init {
        dummyDep.dummy()
    }
}
