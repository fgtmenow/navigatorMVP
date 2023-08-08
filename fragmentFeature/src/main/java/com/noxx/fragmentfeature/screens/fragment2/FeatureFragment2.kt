package com.noxx.fragmentfeature.screens.fragment2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.noxx.fragmentfeature.R
import com.noxx.fragmentfeature.databinding.FragmentFeature2Binding
import com.noxx.navigation.destinations.fragment.ResultApiFragmentDestination
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FeatureFragment2 : Fragment(R.layout.fragment_feature2) {

    private val viewModel: FeatureFragment2ViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentFeature2Binding::bind)

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
        viewBinding.apply {
            button.setOnClickListener {
                viewModel.navigate(ResultApiFragmentDestination.createRoute())
            }
        }
        setFragmentResultListener(ResultApiFragmentDestination.REQUEST_FRAGMENT_KEY) { key, bundle ->
            viewBinding.textView2.text = viewBinding.textView2.text.toString() + "\nResult: " + bundle.getInt("key")
        }
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
