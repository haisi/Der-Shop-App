package li.selman.dershop.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import li.selman.dershop.R
import li.selman.dershop.databinding.FragmentHomeBinding
import li.selman.dershop.ui.article.ArticleFragment
import li.selman.dershop.ui.util.viewBinding
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), HomeAdapter.OnStoryListener {

    private val viewModel by viewModels<HomeViewModel>()

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HomeAdapter(this as HomeAdapter.OnStoryListener)
        binding.homeRv.adapter = adapter
        binding.homeRv.layoutManager = LinearLayoutManager(context)

        subscribeToLiveData(adapter)
    }

    private fun subscribeToLiveData(adapter: HomeAdapter) {
        // Don't pass the fragment as livecycle owner, as we don't want to update the UI when view of the fragment is destroyed.
        // Fragment might still be around, e.g. on the back-stack.
        viewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            adapter.submitList(articles)
            // TODO Hö, why is my differ not working?!
            adapter.notifyDataSetChanged()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            Timber.d("Loading $loading")
            binding.loadingGroup.visibility = if (loading) View.VISIBLE else View.GONE
        })

        viewModel.updatedFavsEvent.observe(viewLifecycleOwner, Observer { event ->
            when (event) {
                is ChangedFavsEvent.Removed -> {
                    Snackbar
                        .make(binding.root, "Removed ${event.storyId}", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            // TODO add undo feature
                        }.show()
                }
                is ChangedFavsEvent.Added -> {
                    Snackbar
                        .make(binding.root, "Added ${event.storyId}", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            // TODO add undo feature
                        }.show()
                }
            }
        })
    }

    override fun onStoryClick(position: Int) {
        val navController = findNavController()
        val bundle = bundleOf(ArticleFragment.ARG_ARTICLE_ID to (position.toLong()))
        navController.navigate(R.id.action_nav_home_to_articleFragment, bundle)
    }

    override fun onFavouriteClick(position: Int) {
        viewModel.favourite(position)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_fetch_today -> {
                Toast.makeText(context, "Today", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_fetch_week -> {
                true
            }
            R.id.action_fetch_month -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
