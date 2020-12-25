package li.selman.dershop.ui.home.grid

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import li.selman.dershop.business.product.Product
import li.selman.dershop.ui.util.BindableViewHolder

class HomeProductAdapter(val clickListener: ProductListener) :
    ListAdapter<Product, BindableViewHolder<Product>>(HomeProductDiffCallback()) {

    enum class ProductTypes {
        A, B
    }

    class ProductListener(val clickListener: (productId: Int) -> Unit) {
        fun onClick(product: Product) = clickListener(product.id)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return if (item.hasDiscount()) {
            ProductTypes.A.ordinal
        } else {
            ProductTypes.B.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<Product> {
        return when (viewType) {
            ProductTypes.A.ordinal -> {
                ProductViewHolder.inflate(parent)
            }
            else -> {
                ProductViewHolder.inflate(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: BindableViewHolder<Product>, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}
