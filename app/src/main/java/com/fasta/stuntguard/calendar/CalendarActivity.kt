package com.fasta.stuntguard.calendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fasta.stuntguard.MainActivity
import com.fasta.stuntguard.R
import com.fasta.stuntguard.databinding.ActivityCalendarBinding
import com.fasta.stuntguard.prediksi.PrediksiActivity
import com.fasta.stuntguard.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        bottomNavigationView = findViewById(R.id.bottom_navigation_calender)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.calender -> {
                    true
                }
                R.id.prediksi -> {
                    startActivity(Intent(this, PrediksiActivity::class.java))
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        //setupView()
    }

//    private fun setupView(){
//        @Suppress("DEPRECATION")
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        window.statusBarColor = Color.TRANSPARENT
//        supportActionBar?.hide()
//        binding.bottomNavigationCalender.selectedItemId = R.id.calendar
//    }
}