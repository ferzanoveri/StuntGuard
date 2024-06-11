package com.fasta.stuntguard.profile

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.fasta.stuntguard.MainActivity
import com.fasta.stuntguard.R
import com.fasta.stuntguard.calendar.CalendarActivity
import com.fasta.stuntguard.databinding.ActivityProfileBinding
import com.fasta.stuntguard.prediksi.PredictionActivity
import com.fasta.stuntguard.profile.edit.EditPasswordActivity
import com.fasta.stuntguard.profile.edit.EditProfileActivity
import com.fasta.stuntguard.profile.family.FamilyMemberActivity
import com.fasta.stuntguard.splashscreen.SplashScreenActivity
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.profile.ProfileViewModel
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
        setUpAction()
        bottomNavigation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()

        factory = ViewModelFactory.getInstance(this)
    }

    private fun setUpAction(){
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
                showDialog()
            }
        }
    }

    private fun bottomNavigation(){
        bottomNavigationView = findViewById(R.id.bottom_navigation_main)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.calender -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
                    true
                }
                R.id.predict -> {
                    startActivity(Intent(this, PredictionActivity::class.java))
                    true
                }
                R.id.profile -> {
                    true
                }
                else -> false
            }
        }
    }
    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            profileViewModel.logout()
            startActivity(Intent(this, SplashScreenActivity::class.java))
            Toast.makeText(this, "You are successfully logged out", Toast.LENGTH_SHORT).show()
            finish()
        }
        builder.setNegativeButton(android.R.string.cancel) { _, _ -> }
        builder.show()
    }
}