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
                is ActionResult.Success -> _articles.value = result.data
                else -> _articles.value = emptyList()
            }
        }
    }

    private val _articles = MutableLiveData<List<ViewedArticleResponse>>(emptyList())
    val articles: LiveData<List<ViewedArticleResponse>> = _articles

    val topArticle: LiveData<ViewedArticleResponse?> = Transformations.map(articles) { it.firstOrNull() }
}
