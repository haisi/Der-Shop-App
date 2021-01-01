package li.selman.dershop.business.popular

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import li.selman.dershop.tech.async.Dispatcher
import li.selman.dershop.tech.async.Type
import li.selman.dershop.tech.network.NytResult
import li.selman.dershop.util.ActionResult
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

    suspend fun findAllStories(of: MostViewOf): ActionResult<List<ViewedArticleResponse>> = withContext(dispatcher) {
        delay(2000)
        return@withContext try {
            val response: NytResult<ViewedArticleResponse> = mostPopularApi.fetchMostViewedArticles(of.days)
            ActionResult.Success(response.results)
        } catch (ex: Exception) {
            ActionResult.Error(ex)
        }
    }
}
