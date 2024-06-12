package com.fasta.stuntguard

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fasta.stuntguard.databinding.ActivityFamilyMemberBinding
import com.fasta.stuntguard.databinding.ActivityFoodRecomendationBinding

class FoodRecomendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodRecomendationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFoodRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.backButton.setOnClickListener {
            onBackPressed()
            finish()
        }
    }
}