package com.fasta.stuntguard.prediksi

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.fasta.stuntguard.MainActivity
import com.fasta.stuntguard.R
import com.fasta.stuntguard.calendar.CalendarActivity
import com.fasta.stuntguard.data.response.Child
import com.fasta.stuntguard.data.response.PredictionResponse
import com.fasta.stuntguard.databinding.ActivityPredictionBinding
import com.fasta.stuntguard.profile.ProfileActivity
import com.fasta.stuntguard.utils.factory.ViewModelFactory
import com.fasta.stuntguard.viewmodel.predict.PredictionViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class PredictionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictionBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var factory: ViewModelFactory
    private val predictionViewModel: PredictionViewModel by viewModels { factory }
    private lateinit var spinnerChildren: Spinner
    private lateinit var editTextWeight: EditText
    private lateinit var editTextHeight: EditText
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
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()

        spinnerChildren = binding.chooseClhild
        editTextWeight = binding.weightNow
        editTextHeight = binding.heightNow
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
        }

        predictionViewModel.children.observe(this) { parentChildResponse ->
            setupSpinner(parentChildResponse.data)
        }
        predictionViewModel.predictionResponse.observe(this) { predictionResponse ->
            Log.d(TAG, "Received prediction response: $predictionResponse")
            showPredictionResult(predictionResponse)
        }
    }

    private fun setupSpinner(children: ArrayList<Child>) {
        val childNames = arrayListOf("Choose child")
        childNames.addAll(children.map { it.childName })

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, childNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerChildren.adapter = adapter

        spinnerChildren.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    val selectedChildId = children[position - 1].childId
                    Log.d(TAG, "Selected Child ID: $selectedChildId")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun performPrediction() {
        val selectedChildId = getSelectedChildId()
        val weight = editTextWeight.text.toString().toFloatOrNull() ?: 0f
        val height = editTextHeight.text.toString().toFloatOrNull() ?: 0f
        val breastfeeding = getBreastfeedingStatus()

        if (selectedChildId.isEmpty()) {
            Toast.makeText(this, "Please select a child.", Toast.LENGTH_SHORT).show()
            return
        }
        if (weight <= 0 || height <= 0) {
            Toast.makeText(this, "Please enter valid weight and height.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "Performing prediction with childId: $selectedChildId, weight: $weight, height: $height, breastfeeding: $breastfeeding")
        predictionViewModel.postPrediction(selectedChildId, weight, height, breastfeeding)
    }


    private fun getSelectedChildId(): String {
        val position = spinnerChildren.selectedItemPosition
        return if (position > 0) {
            predictionViewModel.children.value?.data?.get(position - 1)?.childId ?: ""
        } else {
            ""
        }
    }

    private fun getBreastfeedingStatus(): Boolean? {
        val checkedId = radioGroupBreastfeeding.checkedRadioButtonId
        return when (checkedId) {
            R.id.radioBreastfeedingYes -> true
            R.id.radioBreastfeedingNo -> false
            else -> null
        }
    }

    private fun showPredictionResult(predictionResponse: PredictionResponse) {
        Log.d(TAG, "Received prediction response: $predictionResponse")
        Log.d(TAG, "Predict Result: ${predictionResponse.predictResult}")

        val predictionMessage: String
        if (predictionResponse.predictResult != null) {
            predictionMessage = if (predictionResponse.predictResult == "Yes") {
                "Your child is stunted."
            } else {
                "Your child is not stunted."
            }
        } else {
            predictionMessage = "Error: Prediction result is null."
        }

        val builder = AlertDialog.Builder(this)
            .setTitle("Prediction Result")
            .setMessage(predictionMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        builder.show()
    }


    companion object {
        private const val TAG = "PredictionActivity"
    }
}
