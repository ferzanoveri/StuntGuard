package com.fasta.stuntguard.profile.family

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fasta.stuntguard.adapter.FamilyAdapter
import com.fasta.stuntguard.data.response.Child
import com.fasta.stuntguard.data.response.ParentChildResponse
import com.fasta.stuntguard.databinding.ActivityFamilyMemberBinding
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.profile.ProfileViewModel

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
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
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
            binding.tvItemName.text = user.name
            profileViewModel.getParentChild(user.userId)
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