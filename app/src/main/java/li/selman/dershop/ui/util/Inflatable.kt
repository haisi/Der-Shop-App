package li.selman.dershop.ui.util

import android.view.ViewGroup

interface Inflatable<E> {
    fun inflate(parent: ViewGroup) : BindableViewHolder<E>
}