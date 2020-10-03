package saberliou.demo.profile.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import saberliou.demo.profile.RetrofitClient

class GithubRepositoriesViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        viewModelScope.launch {
            try {
                _response.value = "Success: ${RetrofitClient.githubApiService.getRepositories().size} GithubRepositories received."
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
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
