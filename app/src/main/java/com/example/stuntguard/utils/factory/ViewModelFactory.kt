package com.example.stuntguard.utils.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stuntguard.repository.Repository
import com.example.stuntguard.utils.Injection
import com.example.stuntguard.viewmodel.MainViewModel
import com.example.stuntguard.viewmodel.auth.LoginViewModel
import com.example.stuntguard.viewmodel.auth.RegisterViewModel
import com.example.stuntguard.viewmodel.calendar.CalendarViewModel
import com.example.stuntguard.viewmodel.news.NewsViewModel
import com.example.stuntguard.viewmodel.predict.PredictViewModel
import com.example.stuntguard.viewmodel.profile.ProfileViewModel
import com.example.stuntguard.viewmodel.recommendation.FoodRecommendationViewModel

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }

            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(PredictViewModel::class.java) -> {
                PredictViewModel(repository) as T
            }

            modelClass.isAssignableFrom(FoodRecommendationViewModel::class.java) -> {
                FoodRecommendationViewModel(repository) as T
            }

            modelClass.isAssignableFrom(CalendarViewModel::class.java) -> {
                CalendarViewModel(repository) as T
            }


            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { INSTANCE = it }
    }

}