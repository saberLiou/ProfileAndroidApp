package saberliou.demo.profile.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import saberliou.demo.profile.GithubUser
import saberliou.demo.profile.data.Result
import saberliou.demo.profile.data.source.IGithubRepository

class HomeViewModel @ViewModelInject constructor(
    private val githubRepository: IGithubRepository
) : ViewModel() {
    private val _githubUser = MutableLiveData<GithubUser>()
    val githubUser: LiveData<GithubUser>
        get() = _githubUser

    init {
        viewModelScope.launch {
            val githubUserResult = githubRepository.getGithubUser(true)
            if (githubUserResult is Result.Success) {
                _githubUser.value = githubUserResult.data
            } else {
                _githubUser.value = GithubUser()
            }
        }
    }
}
