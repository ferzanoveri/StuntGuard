package com.fasta.stuntguard.prediksi

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.fasta.stuntguard.MainActivity
import com.fasta.stuntguard.R
import com.fasta.stuntguard.calendar.CalendarActivity
import com.fasta.stuntguard.data.response.Child
import com.fasta.stuntguard.data.response.ParentChildResponse
import com.fasta.stuntguard.databinding.ActivityPredictionBinding
import com.fasta.stuntguard.profile.ProfileActivity
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.predict.PredictionViewModel
import com.fasta.stuntguard.viewmodel.profile.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class PredictionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictionBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var factory: ViewModelFactory
    private val predictionViewModel: PredictionViewModel by viewModels { factory }
    private lateinit var spinnerChildren: Spinner
    private lateinit var editTextWeight: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var editTextAge: EditText
    private lateinit var radioGroupBreastfeeding: RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        setupViewModel()

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()

        spinnerChildren = binding.chooseClhild
        editTextWeight = binding.weightNow
        editTextHeight = binding.heightNow
        editTextAge = binding.ageNow
        radioGroupBreastfeeding = binding.radiogroupBreastfeeding

        binding.btnPrediksi.setOnClickListener {
            performPrediction()
        }
    }

    private fun setupAction(){
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
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        predictionViewModel.getUserData().observe(this) { user ->
            predictionViewModel.getParentChild(user.userId)
            predictionViewModel.parentChild.observe(this) { user ->
                setupData(user)
            }
        }
    }

    private fun setupData(data: ParentChildResponse) {
        setupSpinner(data.data)
    }

    private fun setupSpinner(children: ArrayList<Child>) {
        val spinner: Spinner = binding.chooseClhild
        val childNames = arrayListOf("choose child")
        childNames.addAll(children.map { it.childName })
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, childNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun performPrediction() {
        val selectedChildName = spinnerChildren.selectedItem.toString()
        val currentWeight = editTextWeight.text.toString().toFloatOrNull()
        val currentHeight = editTextHeight.text.toString().toFloatOrNull()
        val currentAge = editTextAge.text.toString().toIntOrNull()
        val breastfeedingStatus = when (radioGroupBreastfeeding.checkedRadioButtonId) {
            R.id.radioBreastfeedingYes -> true
            R.id.radioBreastfeedingNo -> false
            else -> null
        }

        Log.d(TAG, "performPrediction: Weight: $currentWeight, Height: $currentHeight, Age: $currentAge, Breastfeeding: $breastfeedingStatus")

        if (currentWeight == null || currentHeight == null || currentAge == null) {
            Toast.makeText(this, "Please enter valid weight, height, and age", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedChild = predictionViewModel.children.value?.data?.find { it.childName == selectedChildName }

        if (selectedChild != null) {
            Log.d(TAG, "Selected Child: ${selectedChild.childId}")
            predictionViewModel.postPrediction(selectedChild.childId, currentWeight, currentHeight, breastfeedingStatus)
            predictionViewModel.predictionResponse.observe(this) { predictionResponse ->
                Log.d(TAG, "Prediction Response: $predictionResponse")
                predictionResponse?.let {
                    showPredictionResult(it.status)
                } ?: run {
                    Toast.makeText(this, "Prediction failed. Try again.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Selected child not found", Toast.LENGTH_SHORT).show()
        }
    }
    private fun showPredictionResult(isStunted: Boolean) {
        val message = if (isStunted) {
            "The child is predicted to be stunted."
        } else {
            "The child is not predicted to be stunted."
        }

        AlertDialog.Builder(this)
            .setTitle("Prediction Result")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}