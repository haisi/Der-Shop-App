package li.selman.dershop.ui.home

// TODO use API here instead of Repo.
// class NytPagingSource(private val articleRepo: ArticleRepository) : PagingSource<MostViewOf, ViewedArticleResponse>() {
//    override suspend fun load(params: LoadParams<MostViewOf>): LoadResult<MostViewOf, ViewedArticleResponse> {
//        val position = params.key ?: MostViewOf.TODAY // First page should always be the page of today
//
//        return when (val response = articleRepo.findAllStories(position)) {
//            is ActionResult.Success -> {
//                LoadResult.Page(
//                    data = response.data,
//                    prevKey = if (position == MostViewOf.TODAY) null else MostViewOf.values()[position.ordinal - 1],
//                    nextKey = if (position == MostViewOf.MONTH) null else MostViewOf.values()[position.ordinal + 1]
//                )
//            }
//            is ActionResult.Error -> {
//                LoadResult.Error(response.exception.cause ?: Throwable("🤷‍♂️"))
//            }
//        }
//    }
// }
