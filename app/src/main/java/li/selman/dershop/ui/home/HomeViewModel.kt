package li.selman.dershop.ui.home

import androidx.annotation.MainThread
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
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
    val selectedTime: Flow<MostViewOf> = _selectedTime.asFlow()

    private val _searchQuery = savedStateHandle.getLiveData<String>("search_query", "")
    val searchQuery = _searchQuery.asFlow()

    val articles: LiveData<List<ArticleItem>> = flow {
        val results: Flow<List<ArticleItem>> = combineTransform(selectedTime, searchQuery) { time, query ->
            Timber.i("XXX | qqqqery $query")
            val x = articleRepository
                .allStoriesOf(time)
                .onStart { emptyList<ArticleItem>() }
                .map { it.map(::transformToItemModel) }

            emitAll(x)
        }

        this.emitAll(results)
    }.asLiveData(viewModelScope.coroutineContext)

//    private val combination = combine(flowOf(_selectedTime.asFlow(), _searchQuery.asFlow())) { values ->
//    }

//    private val hmm: Flow<List<ArticleItem>> = _selectedTime.combine(_searchQuery) { time, query ->
//            val result = articleRepository.allStoriesOf(time)
//                .onStart { emit(emptyList()) }
//                .map { it.map(::transformToItemModel) }
//    }

//    val articles: LiveData<List<ArticleItem>> = Transformations.switchMap(_selectedTime) { time ->
//        _loading.postValue(true)
//        Transformations.map(articleRepository.allStoriesOf(time)) {
//            _loading.postValue(false) // TODO find more elegant solution, e.g. instead returning concrete type, return wrapper with states
//            it.map(::transformToItemModel)
//        }
//    }

//    val articles: LiveData<List<ArticleItem>> = Transformations.switchMap(DoubleTrigger(_selectedTime, _searchQuery)) { (time: MostViewOf?, query: String?) ->
//        Timber.i("XXX | query $query")
//        _loading.postValue(true)
//        Transformations.map(articleRepository.allStoriesOf(time!!)) {
//            _loading.postValue(false) // TODO find more elegant solution, e.g. instead returning concrete type, return wrapper with states
//            it.map(::transformToItemModel)
//        }
//    }

//    val articles: LiveData<List<ArticleItem>> = Transformations.switchMap(_selectedTime) { time ->
//        Transformations.switchMap(_searchQuery) { query ->
//            Timber.i("XXX | query $query")
//            _loading.postValue(true)
//            Transformations.map(articleRepository.allStoriesOf(time)) {
//                _loading.postValue(false) // TODO find more elegant solution, e.g. instead returning concrete type, return wrapper with states
//                it.map(::transformToItemModel)
//            }
//        }
//    }


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
    }

    @MainThread
    fun changeStoryTime(time: MostViewOf) {
        _selectedTime.value = time
    }

    fun onSearchQueryChange(searchQuery: String?) {
        _searchQuery.value = searchQuery
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
