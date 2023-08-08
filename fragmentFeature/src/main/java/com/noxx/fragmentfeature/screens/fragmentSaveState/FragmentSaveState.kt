package com.noxx.fragmentfeature.screens.fragmentSaveState

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.noxx.fragmentfeature.R
import com.noxx.fragmentfeature.databinding.FragmentFeature1Binding
import com.noxx.fragmentfeature.screens.LogLifecycleFragment
import com.noxx.navigation.destinations.compose.StartComposeDestination
import com.noxx.navigation.destinations.fragment.Fragment2Destination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSaveState : LogLifecycleFragment(R.layout.fragment_feature1) {

    private val viewModel: FragmentSaveStateViewModel by viewModels()
    private val binding by viewBinding(FragmentFeature1Binding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel.counter.observe(viewLifecycleOwner) {
                textView.text = it.toString()
            }

            button.setOnClickListener {
                viewModel.dec()
            }
            button2.setOnClickListener {
                viewModel.inc()
            }
            button3.setOnClickListener {
                viewModel.navigate(Fragment2Destination.createRoute())
            }
            button4.setOnClickListener {
                viewModel.navigateUp()
            }
            button5.setOnClickListener {
                viewModel.navigate(
                    Fragment2Destination.createRoute(),
                    StartComposeDestination.navRoot,
                    true
                )
            }
            button6.setOnClickListener {
                viewModel.closeApp()
            }
            button7.setOnClickListener {
                viewModel.navigate("app/notExist")
            }
            button8.setOnClickListener {
                viewModel.navigateUp("app/notExist")
            }
        }
    }
}
