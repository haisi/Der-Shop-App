package li.selman.dershop.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

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
}