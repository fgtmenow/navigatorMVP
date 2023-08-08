package com.noxx.fragmentfeature.screens.fragmentWithParcelable

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.noxx.fragmentfeature.R
import com.noxx.fragmentfeature.databinding.FragmentWithArgsBinding
import com.noxx.fragmentfeature.screens.LogLifecycleFragment
import com.noxx.navigation.destinations.fragment.FragmentWithParcelableDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentWithParcelable : LogLifecycleFragment(R.layout.fragment_with_args) {

    private val viewModel: FragmentWithParcelableViewModel by viewModels()
    private val binding by viewBinding(FragmentWithArgsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textView2.text = """
                parcelable = ${
            arguments?.getParcelable(FragmentWithParcelableDestination.ARG3) as? FragmentWithParcelableDestination.DataParcelableTestArgument?
            }
            """.trimIndent()

            button9.text = "fragment to compose with parcelable"
            button9.setOnClickListener {
                Toast.makeText(
                    view.context,
                    "navigate to compose with parcel is anti-pattern",
                    Toast.LENGTH_LONG
                ).show()
            }

            button10.text = "fragment to fragment with parcelable"
            button10.setOnClickListener {
                viewModel.navigate(
                    FragmentWithParcelableDestination.createRoute(
                        arg1 = "f to c",
                        arg2 = 158,
                        parcel = FragmentWithParcelableDestination.DataParcelableTestArgument("pp")
                    )
                )
            }
        }
    }
}
