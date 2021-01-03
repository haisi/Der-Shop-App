package li.selman.dershop.business.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import li.selman.dershop.tech.async.Dispatcher
import li.selman.dershop.tech.async.Type
import li.selman.dershop.tech.network.NytResult
import timber.log.Timber
import javax.inject.Inject

@Suppress("MagicNumber")
enum class MostViewOf(val days: Int) {
    TODAY(1),
    WEEK(7),
    MONTH(30)
}

class ArticleRepository
@Inject constructor(
    private val mostPopularApi: MostPopularApi,
    private val articleDao: ArticleDao,
    @Dispatcher(Type.IO) private val dispatcher: CoroutineDispatcher
) {

    companion object {
        private val ARTICLE_ENTITY = ArticleCacheEntity::class.java.name
    }

    suspend fun findArticleById(id: Long): ArticleCacheEntity? = withContext(dispatcher) {
        articleDao.findById(id)
    }

    // TODO liveData for MostViewOf and then use switchMap to load stories accordingly

    fun allStoriesOf(time: MostViewOf): LiveData<List<ArticleCacheEntity>> = liveData {
        // TODO, I could also empty differnet states
        // TODO check whether I am really off the main thread
        // See https://developer.android.com/topic/libraries/architecture/coroutines
        val response: NytResult<ViewedArticleResponse> = mostPopularApi.fetchMostViewedArticles(time.days)
        val newArticles = response.results.map {
            ArticleCacheEntity(
                id = it.id,
                imageUrl = it.media.firstOrNull()?.metadata?.firstOrNull() { it.format == "mediumThreeByTwo210" }?.url ?: "",
                title = it.title,
                articleUrl = it.url
            )
        }

        val updatedRows = articleDao.insertAll(newArticles).count { it > 0 }
        Timber.i("Inserted '${newArticles.size}'; updated '$updatedRows' rows of $ARTICLE_ENTITY")

        emitSource(articleDao.findAllById(newArticles.map { it.id }))
    }

    suspend fun update(entity: ArticleCacheEntity) = withContext(dispatcher) {
        articleDao.update(entity)
        Timber.i("Updated  with id '${entity.id}'")
    }
}
