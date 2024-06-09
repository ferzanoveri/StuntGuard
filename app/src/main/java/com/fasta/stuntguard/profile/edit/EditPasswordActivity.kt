package com.fasta.stuntguard.profile.edit

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fasta.stuntguard.databinding.ActivityEditPasswordBinding
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.profile.ProfileViewModel

class EditPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditPasswordBinding
    private val factory: ViewModelFactory by lazy { ViewModelFactory.getInstance(this) }
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Ubah Password"
            setDisplayHomeAsUpEnabled(true)
        }

        profileViewModel.updatePasswordResponse.observe(this) { response ->
            if (response.status) {
                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to update password: ${response.message}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnEditpassword.setOnClickListener {
            val oldPassword = binding.pwLama.text.toString().trim()
            val newPassword = binding.pwBaru.text.toString().trim()
            val confirmPassword = binding.confirmPwBaru.text.toString().trim()

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword == confirmPassword) {
                profileViewModel.getUserData().observe(this) { user ->
                    if (user != null) {
                        profileViewModel.updatePassword(user.userId, oldPassword, newPassword, confirmPassword)
                    } else {
                        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
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
