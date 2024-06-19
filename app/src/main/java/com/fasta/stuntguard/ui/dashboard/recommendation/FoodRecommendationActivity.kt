package com.example.stuntguard.ui.dashboard.recommendation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.stuntguard.ui.dashboard.news.DetailNewsActivity
import com.example.stuntguard.utils.factory.ViewModelFactory
import com.example.stuntguard.viewmodel.MainViewModel
import com.example.stuntguard.viewmodel.recommendation.FoodRecommendationViewModel
import com.fasta.stuntguard.databinding.ActivityFoodRecommendationBinding

class FoodRecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodRecommendationBinding
    private lateinit var factory: ViewModelFactory
    private val foodRecommendationViewModel: FoodRecommendationViewModel by viewModels { factory }

    private var tokenNews: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFoodRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.backButton.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)


        tokenNews = intent.getStringExtra(RECOMMENDATION_ID) ?: ""
        foodRecommendationViewModel.foodRecom(tokenNews as String)

        foodRecommendationViewModel.foodRecom.observe(this) {food ->
//            CARD 1
            binding.food1.text = food.foodDetails[0].category
            binding.jmlKarbo1.text = "${food.foodDetails[0].carbohydrates.toString()} gr"
            binding.jmlProtein1.text = "${food.foodDetails[0].protein.toString()} gr"
            binding.jmlLemak1.text = "${food.foodDetails[0].fat.toString()} gr"
            binding.jmlSerat1.text = "${food.foodDetails[0].fiber.toString()} gr"

//            CARD 2
            binding.food2.text = food.foodDetails[1].category
            binding.jmlKarbo2.text = "${food.foodDetails[1].carbohydrates.toString()} gr"
            binding.jmlProtein2.text = "${food.foodDetails[1].protein.toString()} gr"
            binding.jmlLemak2.text = "${food.foodDetails[1].fat.toString()} gr"
            binding.jmlSerat2.text = "${food.foodDetails[1].fiber.toString()} gr"

//            CARD 3
            binding.food3.text = food.foodDetails[2].category
            binding.jmlKarbo3.text = "${food.foodDetails[2].carbohydrates.toString()} gr"
            binding.jmlProtein3.text = "${food.foodDetails[2].protein.toString()} gr"
            binding.jmlLemak3.text = "${food.foodDetails[2].fat.toString()} gr"
            binding.jmlSerat3.text = "${food.foodDetails[2].fiber.toString()} gr"

//            CARD 4
            binding.food4.text = food.foodDetails[3].category
            binding.jmlKarbo4.text = "${food.foodDetails[3].carbohydrates.toString()} gr"
            binding.jmlProtein4.text = "${food.foodDetails[3].protein.toString()} gr"
            binding.jmlLemak4.text = "${food.foodDetails[3].fat.toString()} gr"
            binding.jmlSerat4.text = "${food.foodDetails[3].fiber.toString()} gr"

//            CARD 5
            binding.food5.text = food.foodDetails[4].category
            binding.jmlKarbo5.text = "${food.foodDetails[4].carbohydrates.toString()} gr"
            binding.jmlProtein5.text = "${food.foodDetails[4].protein.toString()} gr"
            binding.jmlLemak5.text = "${food.foodDetails[4].fat.toString()} gr"
            binding.jmlSerat5.text = "${food.foodDetails[4].fiber.toString()} gr"
        }

        foodRecommendationViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        const val RECOMMENDATION_ID = "recommendation_id"
    }
}