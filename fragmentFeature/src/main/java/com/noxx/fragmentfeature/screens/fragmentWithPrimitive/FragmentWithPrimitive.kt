package com.noxx.fragmentfeature.screens.fragmentWithPrimitive

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.noxx.fragmentfeature.R
import com.noxx.fragmentfeature.databinding.FragmentWithArgsBinding
import com.noxx.fragmentfeature.screens.LogLifecycleFragment
import com.noxx.navigation.destinations.compose.ComposeWithPrimitiveDestination
import com.noxx.navigation.destinations.fragment.FragmentWithPrimitiveDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentWithPrimitive : LogLifecycleFragment(R.layout.fragment_with_args) {

    private val viewModel: FragmentWithPrimitiveViewModel by viewModels()
    private val binding by viewBinding(FragmentWithArgsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textView2.text = """
                ARG1 = ${arguments?.getString(FragmentWithPrimitiveDestination.ARG1)}
                ARG2 = ${arguments?.getLong(FragmentWithPrimitiveDestination.ARG2)}
            """.trimIndent()

            button9.text = "fragment to compose with primitive"
            button9.setOnClickListener {
                viewModel.navigate(ComposeWithPrimitiveDestination.createRoute("f to c", 158))
            }

            button10.text = "fragment to fragment with primitive"
            button10.setOnClickListener {
                viewModel.navigate(FragmentWithPrimitiveDestination.createRoute("f to c", 158))
            }
        }
    }
}
