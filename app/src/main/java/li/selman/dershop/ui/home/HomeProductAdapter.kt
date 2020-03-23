package li.selman.dershop.ui.home

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeProductAdapter(private val products: List<String> = emptyList()) :
    RecyclerView.Adapter<HomeProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val name: TextView) : RecyclerView.ViewHolder(name)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        // TODO inflate View instead like:
        /*
            val textView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_text_view, parent, false) as TextView
         */
        val textView = TextView(parent.context)

        return ProductViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = products[position]
        holder.name.text = item
    }

    override fun getItemCount(): Int = products.size
}