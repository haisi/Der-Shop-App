package li.selman.dershop.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import li.selman.dershop.R
import li.selman.dershop.databinding.ProductSummaryViewBinding
import li.selman.dershop.ui.util.BindableViewHolder
import li.selman.dershop.ui.util.Inflatable

class HomeProductAdapter :
    ListAdapter<Product, BindableViewHolder<Product>>(HomeProductDiffCallback()) {

    enum class ProductTypes {
        A, B
    }

    class ProductViewHolder(val binding: ProductSummaryViewBinding) : BindableViewHolder<Product>(binding.root) {

        companion object : Inflatable<Product> {
            override fun inflate(parent: ViewGroup): BindableViewHolder<Product> {
                val layoutInflater = LayoutInflater
                    .from(parent.context)

                val binding = ProductSummaryViewBinding.inflate(layoutInflater, parent, false)

                return ProductViewHolder(binding)
            }
        }

        override fun bind(item: Product) {
            // If we need access to resources
            val res = itemView.context.resources
            if (item.hasDiscount()) {
                binding.discountPercent.text = item.discount.toString()
            } else {
                binding.discountPercent.visibility = View.GONE
            }

            binding.sustainable.text = item.name
        }

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
        holder.bind(item)
    }

}