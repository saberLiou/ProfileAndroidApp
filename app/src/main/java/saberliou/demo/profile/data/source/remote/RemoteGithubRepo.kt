package saberliou.demo.profile.data.source.remote

import com.google.gson.annotations.SerializedName

data class RemoteGithubRepo(
    val name: String,

    @SerializedName("private")
    val isPrivate: Boolean,

    @SerializedName("html_url")
    val htmlUrl: String,

    val description: String,

    @SerializedName("stargazers_count")
    val stargazersCount: Int
)
