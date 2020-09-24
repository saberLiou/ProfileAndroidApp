package saberliou.demo.profile.sleepqualitytracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SleepNight.TABLE_NAME)
data class SleepNight(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = NIGHT_ID)
    var nightId: Long = 0L,

    @ColumnInfo(name = QUALITY)
    var quality: Int = -1,

    @ColumnInfo(name = START_TIME)
    val startTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = END_TIME)
    var endTime: Long = startTime
) {
    companion object {
        const val TABLE_NAME = "sleep_nights"

        const val NIGHT_ID = "night_id"
        const val QUALITY = "quality"
        const val START_TIME = "start_time"
        const val END_TIME = "end_time"
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
