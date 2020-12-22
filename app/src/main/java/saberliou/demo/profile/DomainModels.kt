package saberliou.demo.profile

import saberliou.demo.profile.data.source.local.GithubUserEntity
import saberliou.demo.profile.data.source.local.SleepNightEntity

data class GithubUser(
    val id: Long = 0L,
    val name: String = "Unknown User",
    val imageUrl: String = "",
    val followers: Long = 0,
    val following: Long = 0
) {
    companion object {
        fun GithubUser.toDatabaseEntity() = GithubUserEntity(
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

        other as GithubUser

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

data class SleepNight(
    var id: Long = 0L,
    var quality: Int = -1,
    val startTime: Long = System.currentTimeMillis(),
    var endTime: Long = startTime
) {
    companion object {
        fun SleepNight.toDatabaseEntity() = SleepNightEntity(
            id = id,
            quality = quality,
            startTime = startTime,
            endTime = endTime
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SleepNight

        if (quality != other.quality) return false
        if (startTime != other.startTime) return false
        if (endTime != other.endTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = quality
        result = 31 * result + startTime.hashCode()
        result = 31 * result + endTime.hashCode()
        return result
    }
}