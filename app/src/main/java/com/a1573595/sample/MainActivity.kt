package com.a1573595.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.a1573595.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.weekCalendar.setOnSelectedListener {
            Log.e("test_day", it.toString())
        }

        binding.btnScrollable.setOnClickListener {
            binding.weekCalendar.isScrollable = !binding.weekCalendar.isScrollable
        }

        binding.btnTouchable.setOnClickListener {
            binding.weekCalendar.isItemTouchable = !binding.weekCalendar.isItemTouchable
        }
    }
}