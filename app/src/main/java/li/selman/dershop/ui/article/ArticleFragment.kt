package li.selman.dershop.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import li.selman.dershop.R
import li.selman.dershop.databinding.FragmentArticleBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [ArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleFragment : Fragment(R.layout.fragment_article) {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private var articleId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleId = requireArguments().getLong(ARG_ARTICLE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.articleIdTv.text = "Hallo Article $articleId"

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

    companion object {

        const val ARG_ARTICLE_ID = "articleId"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param articleId the id of the article.
         * @return A new instance of fragment ArticleFragment.
         */
        @JvmStatic
        fun newInstance(articleId: Long) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ARTICLE_ID, articleId)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
