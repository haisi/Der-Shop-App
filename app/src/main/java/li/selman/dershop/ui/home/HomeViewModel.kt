package li.selman.dershop.ui.home

import androidx.annotation.MainThread
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import li.selman.dershop.business.popular.ArticleCacheEntity
import li.selman.dershop.business.popular.ArticleRepository
import li.selman.dershop.business.popular.MostViewOf
import li.selman.dershop.ui.util.SingleLiveEvent
import timber.log.Timber

sealed class ChangedFavsEvent {
    data class Added(val storyId: String) : ChangedFavsEvent()
    data class Removed(val storyId: String) : ChangedFavsEvent()
}

class HomeViewModel @ViewModelInject constructor(
    private val articleRepository: ArticleRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedTime: MutableLiveData<MostViewOf> = savedStateHandle.getLiveData<MostViewOf>("selected_time", MostViewOf.WEEK)
    val selectedTime: LiveData<MostViewOf> = _selectedTime

    val articles = Transformations.switchMap(selectedTime) { time ->
        _loading.postValue(true)
        Transformations.map(articleRepository.allStoriesOf(time)) {
            _loading.postValue(false) // TODO find more elegant solution, e.g. instead returning concrete type, return wrapper with states
            it.map(::transformToItemModel)
        }
    }

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean> = _loading

    private val _updatedFavsEvent = SingleLiveEvent<ChangedFavsEvent>()
    val updatedFavsEvent: LiveData<ChangedFavsEvent> = _updatedFavsEvent

    @MainThread
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

    @MainThread
    fun changeStoryTime(time: MostViewOf) {
        _selectedTime.value = time
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
