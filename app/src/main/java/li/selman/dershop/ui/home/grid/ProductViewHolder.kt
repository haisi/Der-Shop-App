package li.selman.dershop.ui.home.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import li.selman.dershop.business.product.Product
import li.selman.dershop.databinding.ProductSummaryViewBinding
import li.selman.dershop.ui.util.BindableViewHolder
import li.selman.dershop.ui.util.Inflatable

class ProductViewHolder private constructor(val binding: ProductSummaryViewBinding) :
    BindableViewHolder<Product>(binding.root) {

    public companion object : Inflatable<Product> {
        override fun inflate(parent: ViewGroup): BindableViewHolder<Product> {
            val layoutInflater = LayoutInflater
                .from(parent.context)

            val binding = ProductSummaryViewBinding.inflate(layoutInflater, parent, false)

            return ProductViewHolder(binding)
        }
    }

    override fun bind(item: Product, listener: Any?) {
        if (listener == null) {
            throw Exception("Must pass a listener")
        }
        if (listener !is HomeProductAdapter.ProductListener) {
            throw Exception("Listener mus be HomeProductAdapter.ProductListener")
        }

        // If we need access to resources
        val res = itemView.context.resources
        binding.product = item
        binding.clickListener = listener
        binding.executePendingBindings()
    }
}
