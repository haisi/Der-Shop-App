package li.selman.dershop.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import li.selman.dershop.R
import li.selman.dershop.ui.util.BindableViewHolder
import li.selman.dershop.ui.util.Inflatable

class HomeProductAdapter(private val products: List<Product> = emptyList()) :
    Adapter<BindableViewHolder<Product>>() {

    enum class ProductTypes {
        A, B
    }

    class ProductViewHolder(itemView: View) : BindableViewHolder<Product>(itemView) {
        private val discountText: TextView = itemView.findViewById(R.id.discount_percent)

        companion object : Inflatable<Product> {
            override fun inflate(parent: ViewGroup): BindableViewHolder<Product> {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.product_summary_view, parent, false)

                return ProductViewHolder(view)
            }
        }

        override fun bind(item: Product) {
            // If we need access to resources
            val res = itemView.context.resources
            discountText.text = item.discount.toString()
        }

    }

    override fun getItemViewType(position: Int): Int {
        val item = products[position]

        // TODO item should be a real class and make class check
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
        val item = products[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = products.size
}