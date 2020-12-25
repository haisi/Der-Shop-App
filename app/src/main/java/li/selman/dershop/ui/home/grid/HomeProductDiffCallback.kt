package li.selman.dershop.ui.home.grid

import androidx.recyclerview.widget.DiffUtil
import li.selman.dershop.business.product.Product

class HomeProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
