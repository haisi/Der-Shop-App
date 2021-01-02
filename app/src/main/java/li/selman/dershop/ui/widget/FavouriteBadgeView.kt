package li.selman.dershop.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import li.selman.dershop.R

// TODO maybe turn into FrameLayout and inflate layout with parent as `this` so that we can have more complicated badge that just a button?
class FavouriteBadgeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageButton(context, attrs, defStyleAttr) {

    var favourite: Boolean = false
        set(value) {
            field = value
            val drawable = if (value) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off
            val contentDescription = if (value) "Is favourite" else "Is not favourite"
            super.setContentDescription(contentDescription)
            super.setBackground(ContextCompat.getDrawable(context, drawable))
        }

    init {
        if (attrs != null) {
            val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.FavouriteBadgeView)
            favourite = attributes.getBoolean(R.styleable.FavouriteBadgeView_favourite, false)
            attributes.recycle()
        }
    }
}
