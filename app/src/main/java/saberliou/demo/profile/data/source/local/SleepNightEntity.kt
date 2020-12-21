package saberliou.demo.profile.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import saberliou.demo.profile.SleepNight

@Entity(tableName = SleepNightEntity.TABLE_NAME)
data class SleepNightEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id: Long = 0L,

    @ColumnInfo(name = QUALITY)
    var quality: Int = -1,

    @ColumnInfo(name = START_TIME)
    val startTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = END_TIME)
    var endTime: Long = startTime
) {
    companion object {
        const val TABLE_NAME = "sleep_nights"

        const val ID = "id"
        const val QUALITY = "quality"
        const val START_TIME = "start_time"
        const val END_TIME = "end_time"

        fun SleepNightEntity.toDomainModel() = SleepNight(
            id = id,
            quality = quality,
            startTime = startTime,
            endTime = endTime
        )

        fun List<SleepNightEntity>.toDomainModels(): List<SleepNight> = map { entity ->
            SleepNight(
                id = entity.id,
                quality = entity.quality,
                startTime = entity.startTime,
                endTime = entity.endTime
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SleepNightEntity

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
