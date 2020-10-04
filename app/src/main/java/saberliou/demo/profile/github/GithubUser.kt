package saberliou.demo.profile.github

import com.google.gson.annotations.SerializedName

data class GithubUser(
    val id: Long,

    @SerializedName("login")
    val name: String,

    @SerializedName("avatar_url")
    val imageUrl: String,

    val followers: Long,

    val following: Long
)