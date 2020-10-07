package saberliou.demo.profile.github

import com.google.gson.annotations.SerializedName

data class GithubUser(
    val id: Long = 0,

    @SerializedName("login")
    val name: String = "Unknown User",

    @SerializedName("avatar_url")
    val imageUrl: String = "https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png",

    val followers: Long = 0,

    val following: Long = 0
)
