package saberliou.demo.profile.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import saberliou.demo.profile.Developer

class HomeViewModel : ViewModel() {
    // Use backing property with read-only LiveData for external and MutableLiveData for internal mutations.
    private val _developer = MutableLiveData<Developer>()
    val developer: LiveData<Developer>
        get() = _developer

    init {
        _developer.value = Developer()
    }

    fun updateDeveloperName(name: String) {
        _developer.value?.name = name
    }

    fun updateDeveloperMotto(motto: String) {
        _developer.value?.motto = motto
    }
}
