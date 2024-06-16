package com.fasta.stuntguard.news

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fasta.stuntguard.R
import com.fasta.stuntguard.adapter.NewsLatestAdapter
import com.fasta.stuntguard.data.response.GetAllNewsResponse
import com.fasta.stuntguard.data.response.News
import com.fasta.stuntguard.databinding.ActivityMainBinding
import com.fasta.stuntguard.databinding.ActivitySeeAllNewsBinding
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.MainViewModel

class SeeAllNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeeAllNewsBinding
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory}
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySeeAllNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

            setupView()
            setupViewModel()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        mainViewModel.getAllNewsLatest()

        mainViewModel.allNewsLatest.observe(this) { news ->
            setupDataLates(news)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
            finish()
        }

    }
    private fun setupDataLates(data: GetAllNewsResponse) {
        setupAdapter(data.result)
    }

    private fun setupAdapter(news: ArrayList<News>) {
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        val adapter = NewsLatestAdapter(news)
        binding.rvNews.adapter = adapter

        adapter.setOnItemClickCallback(object : NewsLatestAdapter.OnItemClickCallback{
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
        val intent = Intent(this@SeeAllNewsActivity, DetailNewsActivity::class.java)
        mainViewModel.allNews.observe(this){
            intent.putExtra(DetailNewsActivity.EXTRA_RESULT_TYPE, it.currentPage)
        }
        intent.putExtra(DetailNewsActivity.EXTRA_TOKEN, news.token)
        startActivity(intent)
        finish()
    }

}