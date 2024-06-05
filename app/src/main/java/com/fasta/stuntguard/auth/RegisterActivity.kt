package com.fasta.stuntguard.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fasta.stuntguard.auth.LoginActivity
import com.fasta.stuntguard.databinding.ActivityRegisterBinding
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.auth.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var factory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

        registerViewModel.registerData.observe(this) { user ->
            if (user.status) {
                AlertDialog.Builder(this).apply {
                    setTitle(user.message)
                    setMessage("Silahkan login terlebih dahulu")
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    create()
                    show()
                }
            }
        }

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        registerViewModel.isError.observe(this) {
            showError(it)
        }
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            val parentName = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phoneNumber = binding.numberEditText.text.toString()
            val pass = binding.passEditText.text.toString()
            val passConfirm = binding.passConfirmEditText.text.toString()

            if (isEmailValid(email) && isPasswordValid(pass) && pass == passConfirm && phoneNumber.isNotEmpty()) {
                registerViewModel.postRegister(parentName, email, phoneNumber, pass, passConfirm)
            } else if (parentName.isEmpty() && email.isEmpty() && pass.isEmpty() && passConfirm.isEmpty() && phoneNumber.isEmpty()) {
                Toast.makeText(this, "Pastikan data anda sudah terisi semua", Toast.LENGTH_SHORT).show()
            } else if (pass != passConfirm) {
                Toast.makeText(this, "Pastikan password sama", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Format email atau password tidak valid", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

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
//        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
//    }
}