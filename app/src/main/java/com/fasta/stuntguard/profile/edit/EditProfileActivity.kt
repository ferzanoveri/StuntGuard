package com.fasta.stuntguard.profile.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.fasta.stuntguard.R
import com.fasta.stuntguard.databinding.ActivityEditProfileBinding
import com.fasta.stuntguard.databinding.ActivityProfileBinding
import com.fasta.stuntguard.utils.UserPreferences
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.profile.ProfileViewModel
import com.fasta.stuntguard.viewmodel.profile.changepassword.ChangePasswordViewModel

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val factory: ViewModelFactory by lazy { ViewModelFactory.getInstance(this) }
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.title = "Edit Profile"
        actionbar?.setDisplayHomeAsUpEnabled(true)


        // Load user data and populate UI
        //loadUserData()

        binding.btnEditprofile.setOnClickListener {
            saveChanges()
        }
    }

//    private fun loadUserData() {
//        viewModel.getUserData().observe(this, { userData ->
//            // Check if userData is not null
//            userData?.let {
//                // Populate UI with user data
//                binding.nama.setText(it.parentName)
//                binding.email.setText(it.email)
//                binding.hp.setText(it.phone ?: "")
//            }
//        })
//    }

    private fun saveChanges() {

        profileViewModel.getUserData().observe(this, { user ->
            val parentId = user.userId // Assume parentId is a property of UserModel
            val parentName = binding.nama.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val phone = binding.hp.text.toString().trim()

            profileViewModel.updateProfile(parentId, parentName, email, phone)

            // Validate input
            if (parentName.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Parent name and email cannot be empty", Toast.LENGTH_SHORT).show()
                return@observe
            }

            // Update profile
            profileViewModel.updateProfile(parentId, parentName, email, phone)

            // Show a toast message or handle UI feedback to indicate that changes are saved
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        })
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