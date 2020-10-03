package saberliou.demo.profile.github

import com.google.gson.annotations.SerializedName

data class GithubRepo(
    val name: String,

    @SerializedName("private")
    val isPrivate: Boolean,

    @SerializedName("html_url")
    val htmlUrl: String,

    val description: String,

    @SerializedName("stargazers_count")
    val stargazersCount: Int
)
