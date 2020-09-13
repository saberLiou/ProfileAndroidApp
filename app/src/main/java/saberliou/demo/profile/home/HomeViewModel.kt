package saberliou.demo.profile.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    // Use backing property with read-only LiveData for external and MutableLiveData for internal mutations.
    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _motto = MutableLiveData<String>()
    val motto: LiveData<String>
        get() = _motto

    init {
        Developer().also {
            _name.value = it.name
            _motto.value = it.motto
        }
    }

    fun updateDeveloperName(name: String) {
        _name.value = name
    }

    fun updateDeveloperMotto(motto: String) {
        _motto.value = motto
    }
}
