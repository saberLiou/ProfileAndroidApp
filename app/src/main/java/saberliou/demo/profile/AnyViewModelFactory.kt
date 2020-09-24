package saberliou.demo.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnyViewModelFactory<T : ViewModel?>(val creator: () -> T) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = creator() as T
}
