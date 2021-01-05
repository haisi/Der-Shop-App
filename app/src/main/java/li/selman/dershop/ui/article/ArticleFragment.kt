package li.selman.dershop.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import li.selman.dershop.R
import li.selman.dershop.databinding.FragmentArticleBinding
import li.selman.dershop.ui.util.viewBinding
import timber.log.Timber

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val binding by viewBinding(FragmentArticleBinding::bind)

    private val args by navArgs<ArticleFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.articleIdTv.text = "Hallo Article ${args.articleId}"

        subscribeToLiveData()
        setupActions()
    }

    private fun setupActions() {
        // TODO: impl
        Timber.d("setupActions not implemented yet!")
    }

    private fun subscribeToLiveData() {
        // TODO
    }
}
