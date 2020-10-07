package saberliou.demo.profile.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import saberliou.demo.profile.ApiStatus
import saberliou.demo.profile.RetrofitClient

class GithubRepositoriesViewModel : ViewModel() {
    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus

    private val _githubUser = MutableLiveData<GithubUser>()
    val githubUser: LiveData<GithubUser>
        get() = _githubUser

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        viewModelScope.launch {
            _apiStatus.value = ApiStatus.LOADING
            try {
                val githubApiService = RetrofitClient.githubApiService
                _githubUser.value = githubApiService.getUser()
                _response.value = "Success: ${githubApiService.getRepositories().size} GithubRepositories received."
                _apiStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                _githubUser.value = GithubUser()
                _response.value = "Failure: ${e.message}"
                _apiStatus.value = ApiStatus.ERROR
            }
        }
//        RetrofitClient.githubApiService.getRepositories().enqueue(object : Callback<List<GithubRepository>> {
//            override fun onResponse(call: Call<List<GithubRepository>>, response: Response<List<GithubRepository>>) {
//                _response.value = "Success: ${response.body()?.size} GithubRepositories received."
//            }
//
//            override fun onFailure(call: Call<List<GithubRepository>>, t: Throwable) {
//                _response.value = "Failure: " + t.message
//            }
//        })
    }
}
