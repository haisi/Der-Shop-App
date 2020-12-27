package li.selman.dershop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import li.selman.dershop.databinding.FragmentHomeBinding
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(layoutInflater)

        subscribeToLiveData()
        setupActions()

        return binding.root
    }

    private fun setupActions() {
        // TODO: impl
        Timber.d("setupActions not implemented yet!")
    }

    private fun subscribeToLiveData() {
        // TODO: impl
        Timber.d("setupActions not implemented yet!")
    }
}
