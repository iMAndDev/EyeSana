package ua.nure.maksymburym.eyesana.ui.base

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: ViewModel?

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            })
    }

    protected open fun onBackPressed() {
        if (findNavController().navigateUp().not()) {
            requireActivity().finish()
        }
    }
}