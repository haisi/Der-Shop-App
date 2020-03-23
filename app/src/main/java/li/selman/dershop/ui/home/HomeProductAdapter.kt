package li.selman.dershop.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import li.selman.dershop.R
import li.selman.dershop.ui.util.BindableViewHolder
import li.selman.dershop.ui.util.Inflatable

class HomeProductAdapter(private val products: List<String> = emptyList()) :
    Adapter<BindableViewHolder<String>>() {

    enum class ProductTypes {
        A, B
    }

    class ProductViewHolder(itemView: View) : BindableViewHolder<String>(itemView) {
        private val discountText: TextView = itemView.findViewById(R.id.discount_percent)

        companion object : Inflatable<String> {
            override fun inflate(parent: ViewGroup): BindableViewHolder<String> {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.product_summary_view, parent, false)

                return ProductViewHolder(view)
            }
        }

        override fun bind(item: String) {
            // If we need access to resources
            val res = itemView.context.resources
            discountText.text = item
        }

    }

    override fun getItemViewType(position: Int): Int {
        val item = products[position]

        // TODO item should be a real class and make class check
        return if (item.startsWith("A")) {
            ProductTypes.A.ordinal
        } else {
            ProductTypes.B.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<String> {
        return when (viewType) {
            ProductTypes.A.ordinal -> {
                ProductViewHolder.inflate(parent)
            }
            else -> {
                ProductViewHolder.inflate(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: BindableViewHolder<String>, position: Int) {
        val item = products[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = products.size
}