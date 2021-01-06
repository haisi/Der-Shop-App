package li.selman.dershop.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import li.selman.dershop.R
import li.selman.dershop.databinding.ItemStoryBinding

class HomeAdapter(private val onStoryListener: OnStoryListener) : ListAdapter<HomeRecyclerItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeRecyclerItem>() {

            override fun areContentsTheSame(oldItem: HomeRecyclerItem, newItem: HomeRecyclerItem): Boolean {
                return oldItem.equals(newItem)
            }

            override fun areItemsTheSame(oldItem: HomeRecyclerItem, newItem: HomeRecyclerItem): Boolean {
                return oldItem.viewType == newItem.viewType
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (HomeRecyclerItem.ViewType.values()[viewType]) {
            HomeRecyclerItem.ViewType.ARTICLE -> ArticleViewHolder(ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false), onStoryListener)
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

    class ArticleViewHolder(private val binding: ItemStoryBinding, private val onStoryListener: OnStoryListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onStoryListener.onStoryClick(absoluteAdapterPosition)
            }
            binding.favouriteBadge.setOnClickListener {
                if (absoluteAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onStoryListener.onFavouriteClick(absoluteAdapterPosition)
            }
        }

        fun bind(articleItem: ArticleItem) {
            with(binding) {
                articleTv.text = articleItem.title
                favouriteBadge.favourite = articleItem.favourite
                Glide
                    .with(articleImageButton.context).load(articleItem.imageUrl)
                    .error(android.R.drawable.stat_notify_error)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(articleImageButton)
            }
        }
    }

    interface OnStoryListener {
        fun onStoryClick(position: Int)
        fun onFavouriteClick(position: Int)
    }
}
