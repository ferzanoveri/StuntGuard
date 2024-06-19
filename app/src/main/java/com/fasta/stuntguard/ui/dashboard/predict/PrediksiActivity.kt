package com.example.stuntguard.ui.dashboard.predict

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import com.example.stuntguard.data.response.children.Child
import com.example.stuntguard.data.response.prediction.PostPredictionResponse
import com.example.stuntguard.ui.dashboard.MainActivity
import com.example.stuntguard.ui.dashboard.calendar.CalendarActivity
import com.example.stuntguard.ui.dashboard.profile.ProfileActivity
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.predict.PredictViewModel
import com.fasta.stuntguard.R
import com.fasta.stuntguard.databinding.ActivityPrediksiBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class PrediksiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrediksiBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var factory: ViewModelFactory
    private val predictionViewModel: PredictViewModel by viewModels { factory }
    private lateinit var spinnerChildren: Spinner
    private lateinit var editTextWeight: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var radioGroupBreastfeeding: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrediksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()

        spinnerChildren = binding.chooseChild
        editTextWeight = binding.weightNow
        editTextHeight = binding.heightNow
        radioGroupBreastfeeding = binding.rgBreastfeed

        binding.btnPrediksi.setOnClickListener {
            performPrediction()
        }
    }

    private fun setupAction(){
        bottomNavigationView = findViewById(R.id.bottom_navigation_main)
        bottomNavigationView.setSelectedItemId(R.id.predict)
        bottomNavigationView.setOnItemSelectedListener { item ->
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

    private fun getBreastfeedingStatus(): String? {
        val checkedId = radioGroupBreastfeeding.checkedRadioButtonId
        return when (checkedId) {
            R.id.rb_yes -> "Yes"
            R.id.rb_no -> "No"
            else -> null
        }
    }

    private fun showPredictionResult(predictionResponse: PostPredictionResponse) {
        Log.d(TAG, "Received prediction response: $predictionResponse")
        Log.d(TAG, "Predict Result: ${predictionResponse.predictResult}")

//        val predictionMessage: String
        if (predictionResponse.predictResult == "Yes") {
            startActivity(Intent(this@PrediksiActivity, ResultActivity::class.java))
        } else {
            startActivity(Intent(this@PrediksiActivity, ResultHealthyActivity::class.java))
        }

//        val builder = AlertDialog.Builder(this)
//            .setTitle("Prediction Result")
//            .setMessage(predictionMessage)
//            .setPositiveButton("OK") { dialog, _ ->
//                dialog.dismiss()
//            }
//        builder.show()
    }


    companion object {
        private const val TAG = "PredictionActivity"
    }
}