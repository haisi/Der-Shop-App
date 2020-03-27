package li.selman.dershop.ui.home.grid

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import li.selman.dershop.business.product.Product

@BindingAdapter("discountFormatted")
@SuppressLint("SetTextI18n")
fun TextView.setDiscountFormatted(item: Product?) {
    item?.let {
        text = item.discount.toString() + " %"

        if (!item.hasDiscount()) {
            visibility = View.GONE
        }
    }
}