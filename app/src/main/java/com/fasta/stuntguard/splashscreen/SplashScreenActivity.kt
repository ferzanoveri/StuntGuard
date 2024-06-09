package com.fasta.stuntguard.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fasta.stuntguard.MainActivity
import com.fasta.stuntguard.auth.LoginActivity
import com.fasta.stuntguard.databinding.ActivitySplashScreenBinding
import com.fasta.stuntguard.utils.Constant
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.profile.ProfileViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var factory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewSetUp()
        setupViewModel()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        profileViewModel.getUserData().observe(this) { user ->
            if (user.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun viewSetUp() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun animationSetUp() {
        binding.logo.alpha = 0f
        binding.logo.animate().setDuration(Constant.SPLASH_DURATION).alpha(1f).withEndAction {
            setupViewModel()
        }
    }
}

