package com.example.stuntguard.ui.dashboard.news

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stuntguard.adapter.NewsAdapter
import com.example.stuntguard.data.response.news.GetAllNewsResponse
import com.example.stuntguard.data.response.news.News
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.MainViewModel
import com.fasta.stuntguard.databinding.ActivitySeeMoreNewsBinding

class SeeMoreNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeeMoreNewsBinding
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySeeMoreNewsBinding.inflate(layoutInflater)
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

        mainViewModel.getAllNewsRelevansi()

        mainViewModel.allNews.observe(this) { news ->
            setupDataRelevansi(news)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
            finish()
        }

    }

    private fun setupDataRelevansi(data: GetAllNewsResponse) {
        setupAdapterRelevansi(data.result)
    }

    private fun setupAdapterRelevansi(news: ArrayList<News>) {
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        val adapter2 = NewsAdapter(news)

        binding.rvNews.adapter = adapter2

        adapter2.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback{
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
        val intent = Intent(this@SeeMoreNewsActivity, DetailNewsActivity::class.java)

        intent.putExtra(DetailNewsActivity.EXTRA_TOKEN, news.token)
        startActivity(intent)
        finish()
    }

}