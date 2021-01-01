package li.selman.dershop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import li.selman.dershop.R
import li.selman.dershop.databinding.FragmentHomeBinding
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
        setupActions()
    }

    private fun setupActions() {
        // TODO: impl
        Timber.d("setupActions not implemented yet!")
    }

    private fun subscribeToLiveData() {
        // Don't pass the fragment as livecycle owner, as we don't want to update the UI when view of the fragment is destroyed.
        // Fragment might still be around, e.g. on the back-stack.
        viewModel.topArticle.observe(viewLifecycleOwner, Observer { topArticle ->
            binding.topTitle.text = topArticle?.title ?: ""
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
