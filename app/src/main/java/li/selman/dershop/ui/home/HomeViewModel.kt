package li.selman.dershop.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import li.selman.dershop.business.popular.ArticleRepository
import li.selman.dershop.business.popular.MostViewOf
import li.selman.dershop.business.popular.ViewedArticleResponse
import li.selman.dershop.util.ActionResult

class HomeViewModel @ViewModelInject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            when (val result = articleRepository.findAllStories(MostViewOf.TODAY)) {
                is ActionResult.Success -> _articles.value = result.data.map(::transformToItemModel)
                else -> _articles.value = emptyList()
            }
        }
    }

    private val _articles = MutableLiveData<List<ArticleItem>>(emptyList())
    val articles: LiveData<List<ArticleItem>> = _articles

    // TODO technically we could turn this into a suspending function and run the transformation off the main-thread
    private fun transformToItemModel(response: ViewedArticleResponse): ArticleItem {
        return ArticleItem(
            title = response.title
        )
    }
}
