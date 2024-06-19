package com.example.stuntguard.ui.dashboard.profile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.stuntguard.ui.auth.LoginActivity
import com.example.stuntguard.ui.dashboard.MainActivity
import com.example.stuntguard.ui.dashboard.calendar.CalendarActivity
import com.example.stuntguard.ui.dashboard.predict.PrediksiActivity
import com.example.stuntguard.ui.dashboard.profile.edit.EditPasswordActivity
import com.example.stuntguard.ui.dashboard.profile.edit.EditProfileActivity
import com.example.stuntguard.ui.dashboard.profile.family.FamilyMemberActivity
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.profile.ProfileViewModel
import com.fasta.stuntguard.R
import com.fasta.stuntguard.databinding.ActivityProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var factory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            ubahProfile.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, EditProfileActivity::class.java))
            }

            ubahPassword.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, EditPasswordActivity::class.java))
            }

            editKeluarga.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, FamilyMemberActivity::class.java))
            }

            logout.setOnClickListener {
                profileViewModel.logout()
                Toast.makeText(this@ProfileActivity, "Logout Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                finish()
            }


        }

        bottomNavigation()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        profileViewModel.getUserData().observe(this){user ->
            binding.tvName.text = user.name
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun logoutButton(){
      binding.logout.setOnClickListener {
            profileViewModel.logout()
            Toast.makeText(this, "Logout Success", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

//    private fun showDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Logout")
//        builder.setMessage("Are you sure you want to log out?")
//        builder.setPositiveButton(android.R.string.ok) { _, _ ->
//            profileViewModel.logout()
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//            Toast.makeText(this, "You are successfully logged out", Toast.LENGTH_SHORT).show()
//        }
//        builder.setNegativeButton(android.R.string.cancel) { _, _ ->
//        }
//        builder.show()
//    }

    private fun bottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_main)
        bottomNavigationView.setSelectedItemId(R.id.profile)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

                R.id.calendar -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
                    true
                }

                R.id.predict -> {
                    startActivity(Intent(this, PrediksiActivity::class.java))
                    true
                }

                R.id.profile -> {
                    true
                }

                else -> false
            }
        }
    }
}