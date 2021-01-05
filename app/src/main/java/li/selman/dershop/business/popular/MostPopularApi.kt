package li.selman.dershop.business.popular

import li.selman.dershop.tech.network.NytResult
import retrofit2.http.GET
import retrofit2.http.Path

// TODO: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
interface MostPopularApi {

    /**
     * [days] can be either 1, 7 or 30
     */
    @GET("mostpopular/v2/viewed/{days}.json")
    suspend fun fetchMostViewedArticles(@Path("days") days: MostViewOf): NytResult<ViewedArticleResponse>
}
