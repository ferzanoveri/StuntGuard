package com.example.stuntguard.ui.dashboard.news

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.stuntguard.data.response.news.DetailNews
import com.example.stuntguard.data.response.news.GetDetailNewsResponse
import com.example.stuntguard.ui.dashboard.MainActivity
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.news.NewsViewModel
import com.fasta.stuntguard.R
import com.fasta.stuntguard.databinding.ActivityDetailNewsBinding

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityDetailNewsBinding
    private val newsViewModel: NewsViewModel by viewModels { factory }

    private var tokenNews: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.backButton.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        tokenNews = intent.getStringExtra(EXTRA_TOKEN) ?: ""

        newsViewModel.getDetailNews(tokenNews as String)

        newsViewModel.detailNews.observe(this) {news ->
            setupData(news)
        }

        newsViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun setupData(data: GetDetailNewsResponse){
        setupDetailData(data.result)
    }

    private fun setupDetailData(news: DetailNews){
        binding.apply {
            headline.text = news.title
            tvAuthor.text = news.authorLabel
            namaPerusahaan.text = news.label
            deskripsi.text = news.content
            Glide.with(imgNews.context)
                .load(news.image)
                .fitCenter()
                .into(imgNews)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        const val EXTRA_TOKEN = "extra_token"
    }
}