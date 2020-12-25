package li.selman.dershop.ui.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BindableViewHolder<E>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: E, listener: Any? = null)
}
