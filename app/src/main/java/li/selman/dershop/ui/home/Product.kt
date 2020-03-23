package li.selman.dershop.ui.home

data class Product(val name: String, val discount: Int? = null) {
    fun hasDiscount() = (discount ?: 0) > 0
}