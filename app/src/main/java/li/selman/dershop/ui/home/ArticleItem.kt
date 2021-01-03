package li.selman.dershop.ui.home

data class ArticleItem(val id: Long, val title: String, val imageUrl: String, var favourite: Boolean) : HomeRecyclerItem {

    override val viewType = HomeRecyclerItem.ViewType.ARTICLE
}
