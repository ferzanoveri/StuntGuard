package com.fasta.stuntguard.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fasta.stuntguard.MainActivity
import com.fasta.stuntguard.R
import com.fasta.stuntguard.auth.LoginActivity
import com.fasta.stuntguard.calendar.CalendarActivity
import com.fasta.stuntguard.databinding.ActivityProfileBinding
import com.fasta.stuntguard.prediksi.PrediksiActivity
import com.fasta.stuntguard.profile.edit.EditPasswordActivity
import com.fasta.stuntguard.profile.edit.EditProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            ubahProfile.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, EditProfileActivity::class.java))
            }

            ubahPassword.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, EditPasswordActivity::class.java))
            }

            logout.setOnClickListener {
                showDialog()
            }
        }

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
                R.id.prediksi -> {
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

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this,"You are successfully logged out", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(android.R.string.cancel) { _, _ ->
        }
        builder.show()
    }
}