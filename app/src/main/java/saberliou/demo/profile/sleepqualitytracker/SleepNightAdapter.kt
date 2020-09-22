package saberliou.demo.profile.sleepqualitytracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_sleep_night.view.*
import saberliou.demo.profile.R
import saberliou.demo.profile.sleepqualitytracker.SleepNightAdapter.SleepNightViewHolder.Companion.createFrom

//class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.SleepNightViewHolder>() {
class SleepNightAdapter : ListAdapter<SleepNight, SleepNightAdapter.SleepNightViewHolder>(object : DiffUtil.ItemCallback<SleepNight>() {
    /**
     * Check whether two object represent the same item, to discover if an item was added, removed or moved for DiffUtil.
     */
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight) = oldItem.nightId == newItem.nightId

    /**
     * Check whether two items have the same data, to detect if the contents of an item have changed for DiffUtil.
     *
     * If the item is a data-class object, simply use '==' to compare them.
     * This method is called only if areItemsTheSame(T, T) returns true for these items.
     */
    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight) = oldItem == newItem
}) {
//    var nights = listOf<SleepNight>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

//    override fun getItemCount() = nights.size

    override fun onBindViewHolder(holder: SleepNightViewHolder, position: Int) {
//        holder.bind(nights[position])
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createFrom(parent)

    /**
     * Use a private constructor to prevent instantiating the [SleepNightViewHolder] without [createFrom] anywhere else.
     */
    class SleepNightViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun createFrom(parent: ViewGroup) = SleepNightViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_sleep_night, parent, false
                )
            )
        }

        private val qualityImage: ImageView = itemView.ivSleepNightQualityImage
        private val qualityTime: TextView = itemView.tvSleepNightQualityTimeText
        private val quality: TextView = itemView.tvSleepNightQualityText

        fun bind(night: SleepNight) {
            val resources = itemView.context.resources
            qualityImage.setImageResource(
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
            qualityTime.text = convertDurationToFormatted(night.startTime, night.endTime, resources)
            quality.text = convertNumericQualityToString(night.quality, resources)
        }
    }
}
