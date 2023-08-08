package com.noxx.fragmentfeature.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import timber.log.Timber

open class LogLifecycleFragment(@LayoutRes val contentLayoutId: Int) : Fragment(contentLayoutId) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("${hashCode()} onAttach")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("${hashCode()} onStart")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)
        Timber.d("${hashCode()} onCreateView")
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("${hashCode()} onViewCreated")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("${hashCode()} onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("${hashCode()} onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("${hashCode()} onDestroyView")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("${hashCode()} onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("${hashCode()} onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("${hashCode()} onDetach")
    }
}
