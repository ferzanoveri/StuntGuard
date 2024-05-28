package com.example.stuntguard.ui.dashboard.profile.family

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stuntguard.adapter.FamilyAdapter
import com.example.stuntguard.data.response.children.Child
import com.example.stuntguard.data.response.children.ParentChildResponse
import com.example.stuntguard.databinding.ActivityFamilyMemberBinding
import com.example.stuntguard.ui.dashboard.predict.PrediksiActivity
import com.example.stuntguard.ui.dashboard.profile.ProfileActivity
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.profile.ProfileViewModel

class FamilyMemberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFamilyMemberBinding
    private lateinit var factory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFamilyMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.addChild.setOnClickListener {
            val intent = Intent(this, FormAddChildActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        profileViewModel.getUserData().observe(this) { user ->
            val usernameText = user.name
            val capitalizedText =
                usernameText.substring(0, 1).uppercase() + usernameText.substring(1)
            binding.tvItemName.text = capitalizedText
            profileViewModel.getParentChild(user.token, user.userId)
            profileViewModel.parentChild.observe(this) {user ->
                setupData(user)
            }

            profileViewModel.isLoading.observe(this){
                showLoading(it)
            }

            profileViewModel.isError.observe(this) {
                showError(it)
            }
        }
    }

    private fun setupData(data: ParentChildResponse){
        setupAdapter(data.data)
    }

    private fun setupAdapter(child: ArrayList<Child>) {
        binding.rvFamily.layoutManager = LinearLayoutManager(this)
        val adapter = FamilyAdapter(child)
        binding.rvFamily.adapter = adapter
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
}