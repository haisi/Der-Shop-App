package li.selman.dershop.business.product

import java.util.concurrent.atomic.AtomicInteger

data class Product(
    val name: String,
    val discount: Int? = null,
    val id: Int = idGenerator.incrementAndGet()
) {

    companion object {
        val idGenerator: AtomicInteger = AtomicInteger(0)
    }

    fun hasDiscount() = (discount ?: 0) > 0
}
