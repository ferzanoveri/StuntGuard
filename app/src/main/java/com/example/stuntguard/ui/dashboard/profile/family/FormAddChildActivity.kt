package com.example.stuntguard.ui.dashboard.profile.family

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.stuntguard.R
import com.example.stuntguard.databinding.ActivityFormAddChildBinding
import com.example.stuntguard.ui.dashboard.MainActivity
import com.example.stuntguard.ui.dashboard.predict.PrediksiActivity
import com.example.stuntguard.ui.dashboard.profile.ProfileActivity
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.profile.ProfileViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class FormAddChildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormAddChildBinding
    private lateinit var factory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels { factory }
    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormAddChildBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        profileViewModel.postChildData.observe(this) { user ->
            if (user.status) {
                AlertDialog.Builder(this).apply {
                    setTitle(user.message)
                    setMessage("Tambah data anak berhasil")
                    setPositiveButton("Lanjut") { dialog, _ ->
                        dialog.dismiss()
                        profileViewModel.resetPostChildData()
                        finish()
                    }
                    create()
                    show()
                }
            }
        }
    }

    private fun setupAction() {
        binding.edtDate.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.addOnPositiveButtonClickListener {
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = Date(it)
                val formattedDate = dateFormatter.format(date)
                binding.edtDate.setText(formattedDate)

                // Mengonversi tanggal ke format ISO 8601 dan mengirim ke ViewModel
                selectedDate = formatDateToISO(date)
            }

            datePicker.show(supportFragmentManager, "DatePicker")
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
            finish()
        }

        binding.btnSubmit.setOnClickListener {
            profileViewModel.getUserData().observe(this) { user ->
                val childName = binding.edtName.text.toString()
                val childGender = rgConverter(binding.rgGender.checkedRadioButtonId)
                val birthDate = selectedDate
                val birthWeight = binding.edtBerat.text.toString().toDouble()
                val birthHeight = binding.edtTinggi.text.toString().toInt()
                val breastfeed = rgConverter(binding.rgBreastfeed.checkedRadioButtonId)

                profileViewModel.postChild(
                    user.token,
                    user.userId,
                    childName,
                    childGender,
                    birthDate,
                    birthWeight,
                    birthHeight,
                    breastfeed
                )
//                Log.d(TAG, "${childName}\n${birthDate}\n${childGender}\n${birthWeight}\n${birthHeight}\n${breastfeed}")
            }
        }
    }

    private fun formatDateToISO(date: Date): String {
        val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        isoDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return isoDateFormat.format(date)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun rgConverter(pick: Int): String {
        return when (pick) {
            R.id.rb_male -> "Male"
            R.id.rb_female -> "Female"
            R.id.rb_yes -> "Yes"
            R.id.rb_no -> "No"
            else -> "Belum pilih"
        }
    }

    companion object {
        private const val TAG = "FormAddChildActivity"
    }
}