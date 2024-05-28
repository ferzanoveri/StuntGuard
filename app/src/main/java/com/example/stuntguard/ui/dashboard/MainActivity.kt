package com.example.stuntguard.ui.dashboard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stuntguard.R
import com.example.stuntguard.adapter.NewsAdapter
import com.example.stuntguard.adapter.NewsLatestAdapter
import com.example.stuntguard.data.response.news.GetAllNewsResponse
import com.example.stuntguard.data.response.news.News
import com.example.stuntguard.databinding.ActivityMainBinding
import com.example.stuntguard.ui.dashboard.calendar.CalendarActivity
import com.example.stuntguard.ui.dashboard.news.DetailNewsActivity
import com.example.stuntguard.ui.dashboard.news.SeeAllNewsActivity
import com.example.stuntguard.ui.dashboard.news.SeeMoreNewsActivity
import com.example.stuntguard.ui.dashboard.predict.PrediksiActivity
import com.example.stuntguard.ui.dashboard.profile.ProfileActivity
import com.example.stuntguard.ui.dashboard.profile.family.FormAddChildActivity
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.MainViewModel
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

                R.id.calendar -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
                    finish()
                    true
                }

                R.id.predict -> {
                    startActivity(Intent(this, PrediksiActivity::class.java))
                    finish()
                    true
                }

                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }

                else -> false
            }
        }
        binding.addChildButton.setOnClickListener {
            val intent = Intent(this, FormAddChildActivity::class.java)
            startActivity(intent)
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
                binding.username.text = usernameText // or set a default text if needed
            }

            mainViewModel.getAllNewsRelevansi(user.token)
            mainViewModel.getAllNewsLatest(user.token)

        }
        mainViewModel.allNews.observe(this) { news ->
            setupDataRelevansi(news)
        }

        mainViewModel.allNewsLatest.observe(this) { news ->
            setupDataLates(news)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.seeAll.setOnClickListener {
            startActivity(Intent(this, SeeAllNewsActivity::class.java))
            finish()
        }

        binding.seeMore.setOnClickListener {
            startActivity(Intent(this, SeeMoreNewsActivity::class.java))
            finish()
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

    private fun setupDataLates(data: GetAllNewsResponse) {
        setupAdapter(data.result)
    }

    private fun setupDataRelevansi(data: GetAllNewsResponse) {
        setupAdapterRelevansi(data.result)
    }

    private fun setupAdapter(news: ArrayList<News>) {
        binding.rvNewsLatest.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = NewsLatestAdapter(news, limit = 10)
        binding.rvNewsLatest.adapter = adapter

        adapter.setOnItemClickCallback(object : NewsLatestAdapter.OnItemClickCallback {
            override fun onItemClicked(news: News) {
                toDetailActivity(news)
            }
        })
    }

    private fun setupAdapterRelevansi(news: ArrayList<News>) {
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        val adapter2 = NewsAdapter(news, limit = 10)

        binding.rvNews.adapter = adapter2

        adapter2.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
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

        intent.putExtra(DetailNewsActivity.EXTRA_TOKEN, news.token)
        startActivity(intent)
        finish()
    }
}