package li.selman.dershop.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import li.selman.dershop.databinding.ItemStoryBinding

class HomeAdapter : ListAdapter<HomeRecyclerItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeRecyclerItem>() {

            override fun areContentsTheSame(oldItem: HomeRecyclerItem, newItem: HomeRecyclerItem): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: HomeRecyclerItem, newItem: HomeRecyclerItem): Boolean {
                return oldItem.viewType == newItem.viewType
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (HomeRecyclerItem.ViewType.values()[viewType]) {
            HomeRecyclerItem.ViewType.ARTICLE -> ArticleViewHolder(ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            HomeRecyclerItem.ViewType.HEADER -> TODO()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (getItemViewType(position)) {
            HomeRecyclerItem.ViewType.ARTICLE.ordinal -> {
                val itemModel = item as ArticleItem
                val viewHolder = holder as ArticleViewHolder
                viewHolder.bind(itemModel)
            }
            HomeRecyclerItem.ViewType.HEADER.ordinal -> {
                TODO()
            }
        }
    }

    class ArticleViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(articleItem: ArticleItem) {
            with(binding) {
                articleTv.text = articleItem.title
            }
        }
    }
}
