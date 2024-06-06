package com.fasta.stuntguard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasta.stuntguard.auth.LoginActivity
import com.fasta.stuntguard.calendar.CalendarActivity
import com.fasta.stuntguard.databinding.ActivityMainBinding
import com.fasta.stuntguard.prediksi.PrediksiActivity
import com.fasta.stuntguard.profile.ProfileActivity
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.profile.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var newsRecyclerView: RecyclerView
    private val mainViewModel: MainViewModel by viewModels { factory }
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        factory = ViewModelFactory.getInstance(this)

        bottomNavigationView = findViewById(R.id.bottom_navigation_main)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    true
                }
                R.id.calender -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
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

        setupViewModel()
        updateGreetingText()
        checkUserLoginStatus()
    }

    private fun checkUserLoginStatus() {
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }


    private fun setupViewModel(){
        profileViewModel.getUserData().observe(this){ user ->
            val usernameText = user.name
            if (usernameText.isNotEmpty()) {
                val capitalizedText = usernameText.substring(0, 1).uppercase() + usernameText.substring(1)
                binding.username.text = capitalizedText
            } else {
                binding.username.text = usernameText // or set a default text if needed
            }

        }
    }

    private fun updateGreetingText() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        val greeting = when (hour) {
            in 0..5 -> "Good Night"
            in 6..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            else -> "Good Evening"
        }
        binding.day.text = greeting
    }

}