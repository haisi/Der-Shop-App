package li.selman.dershop.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import li.selman.dershop.business.popular.ArticleCacheEntity
import li.selman.dershop.business.popular.ArticleRepository
import li.selman.dershop.ui.util.SingleLiveEvent
import timber.log.Timber

sealed class ChangedFavsEvent {
    data class Added(val storyId: String) : ChangedFavsEvent()
    data class Removed(val storyId: String) : ChangedFavsEvent()
}

class HomeViewModel @ViewModelInject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    val articles: LiveData<List<ArticleItem>> = liveData {
        _loading.postValue(true)
        val results = Transformations.map(articleRepository.allStories) {
            _loading.postValue(false) // TODO find more elegant solution, e.g. instead returning concrete type, return wrapper with states
            it.map(::transformToItemModel)
        }
        emitSource(results)
    }

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean> = _loading

    private val _updatedFavsEvent = SingleLiveEvent<ChangedFavsEvent>()
    val updatedFavsEvent: LiveData<ChangedFavsEvent> = _updatedFavsEvent

    fun favourite(position: Int) {

        val articleItem = articles.value?.get(position) ?: return

        viewModelScope.launch {
            val article = articleRepository.findArticleById(articleItem.id)
            Timber.i("Inital $article")
            article?.let {
                it.isFavourite = !it.isFavourite
                articleRepository.update(it)
                val shouldBeDifferent = articleRepository.findArticleById(articleItem.id)
                Timber.i("Should be different $shouldBeDifferent")
            }
        }

        // TODO pass id instead
        val event: ChangedFavsEvent = if (articleItem.favourite) ChangedFavsEvent.Added(articleItem.title) else ChangedFavsEvent.Removed(articleItem.title)
        _updatedFavsEvent.value = event
    }

    // TODO technically we could turn this into a suspending function and run the transformation off the main-thread
    private fun transformToItemModel(entity: ArticleCacheEntity): ArticleItem {
        return ArticleItem(
            id = entity.id,
            title = entity.title,
            imageUrl = entity.imageUrl,
            favourite = entity.isFavourite
        )
    }
}
