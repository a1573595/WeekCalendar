package com.a1573595.weekcalendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a1573595.weekcalendar.databinding.ItemDayBinding
import java.util.*
import kotlin.collections.ArrayList

class WeekAdapter(
    startTimeSeconds: Int,
    private val _borderResource: Int,
    private val _textColor: Int,
    private val _focusedTextColor: Int
) :
    RecyclerView.Adapter<WeekAdapter.DayHolder>() {
    companion object {
        private const val numberOfDaysInWeek = 7
    }

    fun interface OnSelectedListener {
        fun onItemSelected(timeMillis: Long)
    }

    private val calendar: Calendar = Calendar.getInstance()
    private val days: ArrayList<Long> = ArrayList()

    private var width = 0

    private var listener: OnSelectedListener? = null
    private var lastPosition = 0

    init {
        calendar.timeInMillis = startTimeSeconds.toLong() * 1000

        repeat((0 until numberOfDaysInWeek * 2).count()) {
//            val offset: Long =
//                calendar.get(Calendar.ZONE_OFFSET).toLong() + calendar.get(Calendar.DST_OFFSET)
//            val timeInMillis: Long = (calendar.timeInMillis + offset)
//            days.add(timeInMillis)
            days.add(calendar.timeInMillis)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    inner class DayHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            val layoutParams = binding.tvWeek.layoutParams
            layoutParams.width = width
            binding.tvWeek.requestLayout()

            itemView.setOnClickListener {
                binding.tvDay.setBackgroundResource(_borderResource)
                binding.tvDay.setTextColor(_textColor)

                notifyItemChanged(lastPosition)
                lastPosition = adapterPosition

                listener?.onItemSelected(days[adapterPosition])
            }
        }

        fun bind(timeMillis: Long, position: Int) {
            calendar.timeInMillis = timeMillis

            val week =
                calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)
            val mouth = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            binding.tvWeek.text = week?.first().toString()
            binding.tvMonth.text = if (day == 1) mouth else ""
            binding.tvDay.text = day.toString()

            if (lastPosition == position) {
                binding.tvDay.setBackgroundResource(_borderResource)
                binding.tvDay.setTextColor(_focusedTextColor)
            } else {
                binding.tvDay.setBackgroundResource(0)
                binding.tvDay.setTextColor(_textColor)
            }
            binding.tvMonth.setTextColor(_textColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        if (width == 0) {
            width = parent.measuredWidth / numberOfDaysInWeek
        }

        return DayHolder(ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.bind(days[position], position)
    }

    override fun getItemCount(): Int = days.size

    fun setOnSelectedListener(listener: OnSelectedListener) {
        this.listener = listener
    }

    @Synchronized
    fun calculateDays(position: Int, linearLayoutManager: LinearLayoutManager) {
        if (position + numberOfDaysInWeek >= itemCount) {
            calendar.timeInMillis = days[itemCount - 1]

            repeat((0 until numberOfDaysInWeek).count()) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                days.add(calendar.timeInMillis)
            }

            notifyItemRangeInserted(itemCount - 1, itemCount + numberOfDaysInWeek - 1)
        } else if (position < numberOfDaysInWeek - 1) {
            calendar.timeInMillis = days[0]

            repeat((0 until numberOfDaysInWeek).count()) {
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                days.add(0, calendar.timeInMillis)
            }

            linearLayoutManager.scrollToPosition(position + numberOfDaysInWeek)
            notifyItemRangeInserted(0, numberOfDaysInWeek)

            lastPosition += numberOfDaysInWeek
        }
    }
}