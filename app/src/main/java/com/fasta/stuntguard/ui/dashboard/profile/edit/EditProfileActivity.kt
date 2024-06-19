package com.example.stuntguard.ui.dashboard.profile.edit

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.stuntguard.data.model.UserModel
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.profile.ProfileViewModel
import com.fasta.stuntguard.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityEditProfileBinding
    private val profileviewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
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

        binding.btnEditprofile.setOnClickListener {
            profileviewModel.getUserData().observe(this) { user ->

                val parentId = user.userId
                val parentName = binding.nama.text.toString().trim()
                val email = binding.email.text.toString().trim()
                val phone = binding.hp.text.toString().trim()

                if (parentName.isNotEmpty() && email.isNotEmpty() && isEmailValid(email) && isNumberValid(phone)) {
                    profileviewModel.updateProfile(parentId, parentName, email, phone)
                    finish()
                } else if (parentName.isNotEmpty() && email.isNotEmpty() && isEmailValid(email)) {
                    profileviewModel.getDetailUser(user.userId)
                    profileviewModel.detailUser.observe(this) {
                        profileviewModel.updateProfile(parentId, parentName, email, it.data.phone)
                        finish()
                    }
                }
                else if (!isEmailValid(email)) {
                    Toast.makeText(this, "Please use the right email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this,
                        "Parent name and email cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        profileviewModel.updateProfileResponse.observe(this) { user ->
            if (user.status) {
                profileviewModel.loginData.observe(this){
                    saveSession(
                        UserModel(
                            user.data.parentName,
                            it.token,
                            it.userId,
                            true
                        )
                    )
                }
                Toast.makeText(this, user.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveSession(user: UserModel) {
        profileviewModel.saveUser(user)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
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

    private fun isNumberValid(number: String): Boolean {
        return number.length >= 10
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}