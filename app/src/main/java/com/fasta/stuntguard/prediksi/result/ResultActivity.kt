package com.fasta.stuntguard.prediksi.result

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.fasta.stuntguard.R
import com.fasta.stuntguard.utils.factory.ViewModelFactory

class ResultActivity : AppCompatActivity() {
    private lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setupView()
        setupViewModel()

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()

        val actionbar = supportActionBar
        actionbar?.title = "Result Predict"
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
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
}