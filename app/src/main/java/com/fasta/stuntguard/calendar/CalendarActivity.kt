package com.fasta.stuntguard.calendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fasta.stuntguard.FoodRecomendationActivity
import com.fasta.stuntguard.MainActivity
import com.fasta.stuntguard.R
import com.fasta.stuntguard.data.response.Child
import com.fasta.stuntguard.data.response.ParentChildResponse
import com.fasta.stuntguard.databinding.ActivityCalendarBinding
import com.fasta.stuntguard.prediksi.PredictionActivity
import com.fasta.stuntguard.profile.ProfileActivity
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.predict.PredictionViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var factory: ViewModelFactory
    private val predictionViewModel: PredictionViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        bottomNavigation()
        setupViewModel()

    }

    private fun setupView(){
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()

        binding.getFoodRecomendation.setOnClickListener {
            startActivity(Intent(this, FoodRecomendationActivity::class.java))
        }
    }

    private fun bottomNavigation(){
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
                R.id.predict -> {
                    startActivity(Intent(this, PredictionActivity::class.java))
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupData(data: ParentChildResponse) {
        setupSpinner(data.data)
    }
    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        predictionViewModel.getUserData().observe(this) { user ->
            predictionViewModel.getParentChild(user.userId)
            predictionViewModel.parentChild.observe(this) { user ->
                setupData(user)
            }
        }
    }

    private fun setupSpinner(children: ArrayList<Child>) {
        val spinner: Spinner = binding.chooseClhild
        val childNames = arrayListOf("choose child")
        childNames.addAll(children.map { it.childName })
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, childNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}