package li.selman.dershop.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import li.selman.dershop.business.popular.ArticleRepository
import li.selman.dershop.business.popular.MostViewOf
import li.selman.dershop.business.popular.ViewedArticleResponse
import li.selman.dershop.ui.util.SingleLiveEvent
import li.selman.dershop.util.ActionResult

sealed class ChangedFavsEvent {
    data class Added(val storyId: String) : ChangedFavsEvent()
    data class Removed(val storyId: String) : ChangedFavsEvent()
}

class HomeViewModel @ViewModelInject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _articles = MutableLiveData<List<ArticleItem>>(emptyList())
    val articles: LiveData<List<ArticleItem>> = _articles

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean> = _loading

    private val _updatedFavsEvent = SingleLiveEvent<ChangedFavsEvent>()
    val updatedFavsEvent: LiveData<ChangedFavsEvent> = _updatedFavsEvent

    init {
        viewModelScope.launch {
            _loading.value = true
            when (val result = articleRepository.findAllStories(MostViewOf.TODAY)) {
                is ActionResult.Success -> _articles.value = result.data.map(::transformToItemModel)
                else -> _articles.value = emptyList()
            }

            _loading.value = false
        }
    }

    fun favourite(position: Int) {
        val list = _articles.value ?: return
        val articleItem = list[position]
        articleItem.favourite = !articleItem.favourite
        _articles.postValue(list)

        // TODO pass id instead
        val event: ChangedFavsEvent = if (articleItem.favourite) ChangedFavsEvent.Added(articleItem.title) else ChangedFavsEvent.Removed(articleItem.title)
        _updatedFavsEvent.value = event
    }

    // TODO technically we could turn this into a suspending function and run the transformation off the main-thread
    private fun transformToItemModel(response: ViewedArticleResponse): ArticleItem {
        return ArticleItem(
            title = response.title,
            imageUrl = response.media.first().metadata.first { it.format == "mediumThreeByTwo210" }.url,
            favourite = false
        )
    }
}
