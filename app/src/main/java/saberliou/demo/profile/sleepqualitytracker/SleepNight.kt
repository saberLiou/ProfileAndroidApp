package saberliou.demo.profile.sleepqualitytracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SleepNight.tableName)
data class SleepNight(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = nightIdName)
    var nightId: Long = 0L,

    @ColumnInfo(name = qualityName)
    var quality: Int = -1,

    @ColumnInfo(name = startTimeName)
    val startTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = endTimeName)
    var endTime: Long = startTime
) {
    companion object {
        const val tableName = "sleep_nights"
        const val nightIdName = "night_id"
        const val qualityName = "quality"
        const val startTimeName = "start_time"
        const val endTimeName = "end_time"
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
