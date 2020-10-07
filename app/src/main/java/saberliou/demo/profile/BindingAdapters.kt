package saberliou.demo.profile

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import saberliou.demo.profile.sleepqualitytracker.SleepNight
import saberliou.demo.profile.sleepqualitytracker.SleepNightAdapter
import saberliou.demo.profile.sleepqualitytracker.convertDurationToFormatted
import saberliou.demo.profile.sleepqualitytracker.convertNumericQualityToString

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(this.context)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}

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

@BindingAdapter("apiStatus")
fun ImageView.setApiStatus(apiStatus: ApiStatus?) {
    when (apiStatus) {
        ApiStatus.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            visibility = View.GONE
        }
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

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun RecyclerView.setData(data: List<Any>?) {
    data?.let {
        when (adapter) {
            is SleepNightAdapter -> (adapter as SleepNightAdapter).submitListWithHeader(data as List<SleepNight>)
        }
    }
}