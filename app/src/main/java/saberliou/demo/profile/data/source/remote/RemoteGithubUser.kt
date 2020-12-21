package saberliou.demo.profile.data.source.remote

import com.google.gson.annotations.SerializedName
import saberliou.demo.profile.GithubUser

data class RemoteGithubUser(
    val id: Long = 0,

    @SerializedName("login")
    val name: String = "Unknown User",

    @SerializedName("avatar_url")
    val imageUrl: String = "https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png",

    val followers: Long = 0,

    val following: Long = 0
) {
    companion object {
        fun RemoteGithubUser.toDomainModel() = GithubUser(
            id = id,
            name = name,
            imageUrl = imageUrl,
            followers = followers,
            following = following
        )
    }
}
