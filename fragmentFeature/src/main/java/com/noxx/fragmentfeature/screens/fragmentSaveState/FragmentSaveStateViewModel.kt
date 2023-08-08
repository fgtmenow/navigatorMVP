package com.noxx.fragmentfeature.screens.fragmentSaveState

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noxx.fragmentfeature.repo.DummyDep
import com.noxx.navigator.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FragmentSaveStateViewModel @Inject constructor(
    dummyDep: DummyDep,
    navigator: Navigator
) : ViewModel(), Navigator by navigator {

    val counter = MutableLiveData(0)

    init {
        dummyDep.dummy()
    }

    fun inc() {
        counter.value = (counter.value ?: 0) + 1
    }

    fun dec() {
        counter.value = (counter.value ?: 0) - 1
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared")
    }
}
