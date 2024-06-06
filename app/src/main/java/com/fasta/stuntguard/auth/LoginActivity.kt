package com.fasta.stuntguard.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fasta.stuntguard.MainActivity
import com.fasta.stuntguard.data.api.ApiConfig
import com.fasta.stuntguard.data.model.UserModel
import com.fasta.stuntguard.data.response.LoginResponse
import com.fasta.stuntguard.databinding.ActivityLoginBinding
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.auth.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        loginViewModel.loginData.observe(this) { user ->
            if (user.status) {
                loginViewModel.saveUser(
                    UserModel(
                        user.data,
                        user.token,
                        user.userId,
                        true
                    )
                )
                Toast.makeText(this, user.message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        loginViewModel.isError.observe(this) {
            showError(it)
        }
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val pass = binding.passEditText.text.toString()

            if (isEmailValid(email) && isPasswordValid(pass)) {
                loginViewModel.postLogin(email, pass)
            } else {
                Toast.makeText(this, "Format email atau password tidak valid", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveSession(user: UserModel) {
        loginViewModel.saveUser(user)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        val pattern = Regex("^(?=.*[A-Z]).{8,}$")
        return pattern.matches(password)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            Toast.makeText(
                this,
                "Error occured",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        this.transition()
    }

//    private fun transition() {
//        overridePendingTransition(com.google.android.material.R.anim.m3_motion_fade_enter, com.google.android.material.R.anim.m3_motion_fade_exit)
//    }

}
