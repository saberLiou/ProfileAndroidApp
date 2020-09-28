package saberliou.demo.profile.sleepqualitytracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import saberliou.demo.profile.R

@BindingAdapter("qualityImage")
fun ImageView.setQualityImage(night: SleepNight?) {
    night?.let {
        setImageResource(
            when (night.quality) {
                0 -> R.drawable.ic_sleep_quality_0
                1 -> R.drawable.ic_sleep_quality_1
                2 -> R.drawable.ic_sleep_quality_2
                3 -> R.drawable.ic_sleep_quality_3
                4 -> R.drawable.ic_sleep_quality_4
                5 -> R.drawable.ic_sleep_quality_5
                else -> R.drawable.ic_sleep_quality_default
            }
        )
    }
}

@BindingAdapter("formattedDuration")
fun TextView.setFormattedDuration(night: SleepNight?) {
    night?.let {
        text = convertDurationToFormatted(night.startTime, night.endTime, context.resources)
    }
}

@BindingAdapter("quality")
fun TextView.setQuality(night: SleepNight?) {
    night?.let {
        text = convertNumericQualityToString(night.quality, context.resources)
    }
}
