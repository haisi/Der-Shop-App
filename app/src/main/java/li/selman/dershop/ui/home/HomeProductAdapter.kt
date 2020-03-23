package li.selman.dershop.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_summary_view.view.*
import li.selman.dershop.R

class HomeProductAdapter(private val products: List<String> = emptyList()) :
    RecyclerView.Adapter<HomeProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val discountText: TextView = itemView.findViewById(R.id.discount_percent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.product_summary_view, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = products[position]
        holder.discountText.text = item
    }

    override fun getItemCount(): Int = products.size
}