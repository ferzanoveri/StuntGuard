package com.example.stuntguard.ui.dashboard.calendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.stuntguard.data.response.children.Child
import com.example.stuntguard.data.response.children.ParentChildResponse
import com.example.stuntguard.data.response.news.News
import com.example.stuntguard.data.response.prediction.ChildPrediction
import com.example.stuntguard.ui.dashboard.MainActivity
import com.example.stuntguard.ui.dashboard.news.DetailNewsActivity
import com.example.stuntguard.ui.dashboard.predict.PrediksiActivity
import com.example.stuntguard.ui.dashboard.profile.ProfileActivity
import com.example.stuntguard.ui.dashboard.recommendation.FoodRecommendationActivity
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.calendar.CalendarViewModel
import com.fasta.stuntguard.R
import com.fasta.stuntguard.databinding.ActivityCalendarBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var spinnerChildren: Spinner
    private val calendarViewModel: CalendarViewModel by viewModels { factory }
    private lateinit var adapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        calendarViewModel.getUserData().observe(this) { user ->
            calendarViewModel.getParentChild(user.userId)
            calendarViewModel.parentChild.observe(this) { user ->
                setupSpinner(user.data)
            }
        }

        calendarViewModel.predictByChildId.observe(this){
            binding.weightChild.text = it.data[0].childWeight.toString()
            binding.heightChild.text = it.data[0].childHeight.toString()
        }

        calendarViewModel.isLoading.observe(this){
            showLoading(it)
        }

        spinnerChildren = binding.chooseChild

        calendarViewModel.parentChild.observe(this){
            if (it.data.isEmpty()){
                binding.getFoodRecomendation.visibility = View.GONE
            }
        }

        calendarViewModel.predictByChildId.observe(this) {
            binding.weightChild.text = it.data[0].childWeight.toString()
            binding.heightChild.text = it.data[0].childHeight.toString()
            updateStuntingStatus(it.data[0])
        }

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()

    }

//    private fun updateStuntingStatus(isStunting: Boolean) {
//        val stuntingStatus = binding.stuntingStatus
//        val indicator = binding.indicator
//
//        if (isStunting) {
//            stuntingStatus.text = "Stunting"
//            indicator.setBackgroundResource(R.drawable.indicator_red)
//        } else {
//            stuntingStatus.text = "Not Stunting"
//            indicator.setBackgroundResource(R.drawable.indicator_green)
//        }
//    }

    private fun setupAction() {
        binding.getFoodRecomendation.setOnClickListener {
            calendarViewModel.predictByChildId.observe(this){
                toDetailActivity(it.data[0].predictId)
            }
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation_calender)
        bottomNavigationView.setSelectedItemId(R.id.calendar)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

                R.id.calendar -> {
                    true
                }

                R.id.predict -> {
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
    }

    private fun updateStuntingStatus(prediction: ChildPrediction) {
        val stuntingIndicator = binding.stuntingIndicator
        val stuntingStatus = binding.stuntingStatus

        if (prediction.predictResult == 1) {
            stuntingIndicator.setBackgroundResource(R.drawable.indicator_red)
            stuntingStatus.text = "Stunting"
        } else {
            stuntingIndicator.setBackgroundResource(R.drawable.indicator_green)
            stuntingStatus.text = "Not Stunting"
        }

        stuntingIndicator.visibility = View.VISIBLE
    }

    private fun setupSpinner(children: ArrayList<Child>) {
        val childNames = arrayListOf("Choose child")
        childNames.addAll(children.map { it.childName })

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, childNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerChildren.adapter = adapter

        spinnerChildren.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val selectedChildId = children[position - 1].childId
                    calendarViewModel.getPredictionByChildId(selectedChildId)
                    Log.d(TAG, "Selected Child ID: $selectedChildId")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun toDetailActivity(recom: String) {
        val intent = Intent(this@CalendarActivity, FoodRecommendationActivity::class.java)

        intent.putExtra(FoodRecommendationActivity.RECOMMENDATION_ID, recom)
        startActivity(intent)
    }

    companion object {
        private const val TAG = "CalendarActivity"
    }
}