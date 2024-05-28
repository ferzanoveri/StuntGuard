package com.example.stuntguard.ui.dashboard.predict

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stuntguard.R
import com.example.stuntguard.databinding.ActivityResultHealthyBinding
import com.example.stuntguard.ui.dashboard.calendar.CalendarActivity
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.predict.PredictViewModel

class ResultHealthyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultHealthyBinding
    private lateinit var factory: ViewModelFactory
    private val predictionViewModel: PredictViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultHealthyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun setupAction() {
        binding.backButton.setOnClickListener {
            onBackPressed()
            predictionViewModel.resetPostPredictionData()
            startActivity(Intent(this@ResultHealthyActivity, PrediksiActivity::class.java))
            finish()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }
}