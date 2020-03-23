package li.selman.dershop.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import li.selman.dershop.R

class HomeProductAdapter(private val products: List<String> = emptyList()) :
    Adapter<ViewHolder>() {

    class ProductViewHolder(itemView: View) : ViewHolder(itemView) {
        private val discountText: TextView = itemView.findViewById(R.id.discount_percent)

        fun bind(item: String) {
            // If we need access to resources
            val res = itemView.context.resources
            discountText.text = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.product_summary_view, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = products[position]
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(item)
            }
            else -> {
                throw IllegalStateException("Unknown ViewHolder of type ${holder.javaClass}")
            }
        }

    }

    override fun getItemCount(): Int = products.size
}