package com.fasta.stuntguard.news

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.fasta.stuntguard.MainActivity
import com.fasta.stuntguard.data.response.DetailNews
import com.fasta.stuntguard.data.response.GetDetailNewsResponse
import com.fasta.stuntguard.databinding.ActivityDetailNewsBinding
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.NewsViewModel

class DetailNewsActivity : AppCompatActivity() {
    private lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityDetailNewsBinding
    private val newsViewModel: NewsViewModel by viewModels { factory }

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val actionbar = supportActionBar
//        actionbar?.title = "Detail News"
//        actionbar?.setDisplayHomeAsUpEnabled(true)

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

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        val page = intent.getIntExtra(EXTRA_PAGE, 0)
        token = intent.getStringExtra(EXTRA_TOKEN) ?: ""


        newsViewModel.getDetailNews(page, token as String)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupData(data: GetDetailNewsResponse){
        setupDetailData(data.result)
    }

    private fun setupDetailData(news: DetailNews){
        binding.apply {
            headline.text = news.title
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

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

    companion object{
        const val EXTRA_PAGE = "extra_page"
        const val EXTRA_TOKEN = "extra_token"
    }
}