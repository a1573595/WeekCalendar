package com.a1573595.weekcalendar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.use
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.a1573595.weekcalendar.databinding.LayoutWeekCalendarBinding

class WeekCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    @DrawableRes
    private var _startTimeSeconds: Int = (System.currentTimeMillis() / 1000).toInt()

    @DrawableRes
    private var _borderResource: Int = R.drawable.oval_gray

    @ColorInt
    private var _textColor = Color.WHITE

    var isScrollable = true

    var isItemTouchable = true

    private val binding =
        LayoutWeekCalendarBinding.inflate(LayoutInflater.from(context), this, true)

    private val snapHelper = PagerSnapHelper()
    private val linearLayoutManager: LinearLayoutManager =
        object : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return isScrollable
            }

            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    private val weekAdapter: WeekAdapter

    init {
        obtainStyledAttributes(attrs, defStyleAttr)

        snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.setHasFixedSize(true)

        weekAdapter = WeekAdapter(_startTimeSeconds, _borderResource, _textColor)
        binding.recyclerView.adapter = weekAdapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == SCROLL_STATE_IDLE) {
                    snapHelper.findSnapView(linearLayoutManager)?.let {
                        val currentPosition = linearLayoutManager.getPosition(it)
                        linearLayoutManager.scrollToPosition(currentPosition)

                        weekAdapter.calculateDays(currentPosition, linearLayoutManager)
                    }
                }
            }
        })

        weekAdapter.calculateDays(0, linearLayoutManager)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (!isItemTouchable) {
            when (event.action and event.actionMasked) {
                MotionEvent.ACTION_UP -> {
                    return true
                }
            }
        }

        return super.onInterceptTouchEvent(event)
    }

    private fun obtainStyledAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.WeekCalendar,
            defStyleAttr,
            0
        ).use {
            _startTimeSeconds = it.getInt(
                R.styleable.WeekCalendar_wc_startTimeSeconds,
                _startTimeSeconds
            )
            _borderResource = it.getResourceId(
                R.styleable.WeekCalendar_wc_borderRes,
                _borderResource
            )
            _textColor = it.getColor(
                R.styleable.WeekCalendar_wc_textColor,
                _textColor
            )
            isScrollable = it.getBoolean(
                R.styleable.WeekCalendar_wc_isScrollable,
                isScrollable
            )
            isItemTouchable = it.getBoolean(
                R.styleable.WeekCalendar_wc_isItemTouchable,
                isItemTouchable
            )
        }
    }

    fun setOnSelectedListener(listener: WeekAdapter.OnSelectedListener) =
        weekAdapter.setOnSelectedListener(listener)
}