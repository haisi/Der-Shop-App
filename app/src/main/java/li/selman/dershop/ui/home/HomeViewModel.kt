package li.selman.dershop.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import li.selman.dershop.business.popular.ArticleRepository

class HomeViewModel @ViewModelInject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
        }
    }
}
