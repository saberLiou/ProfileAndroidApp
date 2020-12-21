package saberliou.demo.profile.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import saberliou.demo.profile.GithubUser

@Entity(tableName = GithubUserEntity.TABLE_NAME)
data class GithubUserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Long = 0,

    @ColumnInfo(name = NAME)
    val name: String = "Unknown User",

    @ColumnInfo(name = IMAGE_URL)
    val imageUrl: String = "",

    @ColumnInfo(name = FOLLOWERS)
    val followers: Long = 0,

    @ColumnInfo(name = FOLLOWING)
    val following: Long = 0
) {
    companion object {
        const val TABLE_NAME = "github_users"

        const val ID = "id"
        const val NAME = "name"
        const val IMAGE_URL = "image_url"
        const val FOLLOWERS = "followers"
        const val FOLLOWING = "following"

        fun GithubUserEntity.toDomainModel() = GithubUser(
            id = id,
            name = name,
            imageUrl = imageUrl,
            followers = followers,
            following = following
        )

        fun List<GithubUserEntity>.toDomainModels(): List<GithubUser> = map { entity ->
            GithubUser(
                id = entity.id,
                name = entity.name,
                imageUrl = entity.imageUrl,
                followers = entity.followers,
                following = entity.following
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GithubUserEntity

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
