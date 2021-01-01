package li.selman.dershop.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import li.selman.dershop.R

private const val ARG_PARAM1 = "articleId"

/**
 * A simple [Fragment] subclass.
 * Use the [ArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleFragment : Fragment() {
    private var articleId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            articleId = it.getLong(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // TODO why not pass to super-ctor?
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    companion object {
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
                    putLong(ARG_PARAM1, articleId)
                }
            }
    }
}