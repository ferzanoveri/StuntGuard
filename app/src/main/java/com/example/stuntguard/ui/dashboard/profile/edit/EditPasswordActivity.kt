package com.example.stuntguard.ui.dashboard.profile.edit

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stuntguard.R
import com.example.stuntguard.databinding.ActivityEditPasswordBinding
import com.example.stuntguard.ui.dashboard.predict.PrediksiActivity
import com.example.stuntguard.ui.dashboard.profile.ProfileActivity
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.MainViewModel
import com.example.stuntguard.viewmodel.profile.ProfileViewModel

class EditPasswordActivity : AppCompatActivity() {

    private lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityEditPasswordBinding
    private val profileviewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditPasswordBinding.inflate(layoutInflater)
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

        binding.btnEditpassword.setOnClickListener {
            profileviewModel.getUserData().observe(this) { user ->

                val parentId = user.userId
                val oldPass = binding.pwLama.text.toString().trim()
                val newPass = binding.pwBaru.text.toString().trim()
                val confirmPass = binding.confirmPwBaru.text.toString().trim()

                if (oldPass.isNotEmpty() && newPass.isNotEmpty() && confirmPass.isNotEmpty() && isPasswordValid(
                        newPass
                    ) && isPasswordValid(confirmPass)
                ) {
                    profileviewModel.getUserData().observe(this){user ->
                        profileviewModel.updatePassword(user.token, parentId, oldPass, newPass, confirmPass)
                    }
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                } else if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(this, "All input must filled", Toast.LENGTH_SHORT).show()
                } else if (!isPasswordValid(newPass) && !isPasswordValid(confirmPass)) {
                    Toast.makeText(this, "Password must contain 8 character", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        profileviewModel.updatePasswordResponse.observe(this) { user ->
            if (user.status) {
                Toast.makeText(this, user.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, user.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            Toast.makeText(
                this,
                "Username atau Password belum terdaftar",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}