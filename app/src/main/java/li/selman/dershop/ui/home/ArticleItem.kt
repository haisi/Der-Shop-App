package li.selman.dershop.ui.home

data class ArticleItem(val title: String) : HomeRecyclerItem {

    override val viewType = HomeRecyclerItem.ViewType.ARTICLE
}
