package saberliou.demo.profile.sleepqualitytracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import saberliou.demo.profile.R
import saberliou.demo.profile.SleepNight
import saberliou.demo.profile.databinding.ItemSleepNightBinding
import saberliou.demo.profile.sleepqualitytracker.SleepNightAdapter.SleepNightItemViewHolder.Companion.createFrom

class SleepNightAdapter(private val onSleepNightClickListener: OnSleepNightClickListener) :
    ListAdapter<SleepNightAdapter.SleepNightWithHeader, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<SleepNightWithHeader>() {
        override fun areItemsTheSame(oldItem: SleepNightWithHeader, newItem: SleepNightWithHeader) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: SleepNightWithHeader,
            newItem: SleepNightWithHeader
        ) = oldItem == newItem
    }) {
    fun submitListWithHeader(nights: List<SleepNight>?) {
        CoroutineScope(Dispatchers.Default).launch {
            val nightsWithHeader = when (nights) {
                null -> listOf(SleepNightWithHeader.Header)
                else -> listOf(SleepNightWithHeader.Header) + nights.map {
                    SleepNightWithHeader.Item(it)
                }
            }
            withContext(Dispatchers.Main) {
                submitList(nightsWithHeader)
            }
        }
    }

    override fun getItemViewType(position: Int) = getItem(position).viewType

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SleepNightItemViewHolder -> {
                holder.bind(
                    (getItem(position) as SleepNightWithHeader.Item).night,
                    onSleepNightClickListener
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SleepNightWithHeader.VIEW_TYPE_HEADER -> SleepNightHeaderViewHolder.createFrom(parent)
            else -> createFrom(parent)
        }
    }

    interface OnSleepNightClickListener {
        fun onSleepNightClicked(night: SleepNight)
    }

    class SleepNightHeaderViewHolder private constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        companion object {
            fun createFrom(parent: ViewGroup) = SleepNightHeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_sleep_night, parent, false)
            )
        }
    }

    /**
     * Use a private constructor to prevent instantiating the [SleepNightItemViewHolder] without [createFrom] anywhere else.
     */
    class SleepNightItemViewHolder private constructor(private val binding: ItemSleepNightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun createFrom(parent: ViewGroup) = SleepNightItemViewHolder(
                ItemSleepNightBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        fun bind(
            night: SleepNight,
            onSleepNightClickListener: OnSleepNightClickListener
        ) {
            binding.night = night
            binding.onSleepNightClickListener = onSleepNightClickListener
            // Evaluates the pending bindings, updating any Views that have expressions bound to modified variables immediately, must be run on the UI thread.
            // To avoid scheduling updated bindings by other Threads but UI Thread doesn't applied these changes to View,
            // or every time the binding update could cause a View to change its size and postponing the calculation in the next frame could cause the measurement to read wrong values.
            binding.executePendingBindings()
        }
    }

    sealed class SleepNightWithHeader(val viewType: Int) {
        companion object {
            const val VIEW_TYPE_HEADER = 0
            const val VIEW_TYPE_ITEM = 1
        }

        abstract val id: Long

        object Header : SleepNightWithHeader(VIEW_TYPE_HEADER) {
            override val id = Long.MIN_VALUE
        }

        data class Item(val night: SleepNight) :
            SleepNightWithHeader(VIEW_TYPE_ITEM) {
            override val id = night.id
        }
    }
}
