package com.fasta.stuntguard.prediksi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fasta.stuntguard.MainActivity
import com.fasta.stuntguard.R
import com.fasta.stuntguard.databinding.ActivityMainBinding
import com.fasta.stuntguard.databinding.ActivityPrediksiBinding
import com.fasta.stuntguard.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class PrediksiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrediksiBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrediksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        bottomNavigationView = findViewById(R.id.bottom_navigation_main)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
//                R.id.calender -> {
//                    startActivity(Intent(this, PrediksiActivity::class.java))
//                    true
//                }
                R.id.prediksi -> {
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
}