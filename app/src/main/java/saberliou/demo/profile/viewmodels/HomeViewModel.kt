package saberliou.demo.profile.viewmodels

import androidx.lifecycle.ViewModel
import saberliou.demo.profile.Developer

class HomeViewModel : ViewModel() {
    private val developer = Developer()

    fun getDeveloper() = developer

    fun updateDeveloperName(name: String) {
        developer.name = name
    }

    fun updateDeveloperMotto(motto: String) {
        developer.motto = motto
    }
}
