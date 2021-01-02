package li.selman.dershop.ui.home

interface HomeRecyclerItem {

    enum class ViewType {
        ARTICLE, HEADER
    }

    val viewType: ViewType
}
