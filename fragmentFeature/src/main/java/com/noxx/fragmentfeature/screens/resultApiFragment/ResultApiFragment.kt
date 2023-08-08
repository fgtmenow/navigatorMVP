package com.noxx.fragmentfeature.screens.resultApiFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.noxx.fragmentfeature.R
import com.noxx.fragmentfeature.databinding.FragmentResultBinding
import com.noxx.navigation.destinations.fragment.ResultApiFragmentDestination
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ResultApiFragment : Fragment(R.layout.fragment_result) {

    private val viewModel: ResultApiFragmentViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentResultBinding::bind)

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
        viewModel.viewModelScope
        lifecycleScope
        Timber.d("${hashCode()} onViewCreated")
        viewBinding.apply {
            button.setOnClickListener {
                setFragmentResult(ResultApiFragmentDestination.REQUEST_FRAGMENT_KEY, Bundle().apply { putInt("key", 1147) })
                viewModel.navigateUp()
            }
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
