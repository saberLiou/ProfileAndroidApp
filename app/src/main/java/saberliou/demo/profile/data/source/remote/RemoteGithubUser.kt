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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RemoteGithubUser

        if (id != other.id) return false
        if (name != other.name) return false
        if (imageUrl != other.imageUrl) return false
        if (followers != other.followers) return false
        if (following != other.following) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + followers.hashCode()
        result = 31 * result + following.hashCode()
        return result
    }
}
