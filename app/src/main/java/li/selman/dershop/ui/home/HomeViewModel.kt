package li.selman.dershop.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import li.selman.dershop.business.product.Product

class HomeViewModel : ViewModel() {

    /*
     * All products
     */
    private val _products = MutableLiveData<List<Product>>().apply {
        value = listOf(
            Product("Eins"),
            Product("Zwei", 10),
            Product("Drei"),
            Product("Vier"),
            Product("Fünf"),
            Product("Sechs"),
            Product("Sieben"),
            Product("Acht")
        )
    }

    val products: LiveData<List<Product>> = _products

    /*
     * Navigating to product details given an id
     */
    private val _navigateToProductDetails = MutableLiveData<Int>()
    val navigateToProductDetails
        get() = _navigateToProductDetails

    fun onProductClicked(id: Int) {
        _navigateToProductDetails.value = id
    }
    fun onProductDetailsNavigated() {
        _navigateToProductDetails.value = null
    }
}