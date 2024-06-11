package com.fasta.stuntguard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fasta.stuntguard.calendar.CalendarActivity
import com.fasta.stuntguard.data.response.GetAllNewsResponse
import com.fasta.stuntguard.data.response.News
import com.fasta.stuntguard.databinding.ActivityMainBinding
import com.fasta.stuntguard.news.DetailNewsActivity
import com.fasta.stuntguard.prediksi.PredictionActivity
import com.fasta.stuntguard.profile.ProfileActivity
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.MainViewModel
import com.fasta.stuntguard.adapter.NewsAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private val mainViewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_main)
        bottomNavigationView.setSelectedItemId(R.id.home)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    true
                }

                R.id.calender -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
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

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()

        updateGreetingText()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        mainViewModel.getUserData().observe(this) { user ->
            val usernameText = user.name
            if (usernameText.isNotEmpty()) {
                val capitalizedText =
                    usernameText.substring(0, 1).uppercase() + usernameText.substring(1)
                binding.username.text = capitalizedText
            } else {
                binding.username.text = usernameText
            }
        }

        mainViewModel.getAllNews()

        mainViewModel.allNews.observe(this) { news ->
            setupData(news)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
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

    private fun setupData(data: GetAllNewsResponse) {
        setupAdapter(data.result)
    }

    private fun setupAdapter(news: ArrayList<News>) {
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        val adapter = NewsAdapter(news)
        binding.rvNews.adapter = adapter

        adapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback{
            override fun onItemClicked(news: News) {
                toDetailActivity(news)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun toDetailActivity(news: News) {
        val intent = Intent(this@MainActivity, DetailNewsActivity::class.java)
        mainViewModel.allNews.observe(this){
            intent.putExtra(DetailNewsActivity.EXTRA_PAGE, it.currentPage)
        }
        intent.putExtra(DetailNewsActivity.EXTRA_TOKEN, news.token)
        startActivity(intent)
        finish()
    }
}