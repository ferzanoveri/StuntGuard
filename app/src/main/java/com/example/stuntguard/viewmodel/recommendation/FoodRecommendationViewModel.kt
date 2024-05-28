package com.example.stuntguard.viewmodel.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stuntguard.data.model.UserModel
import com.example.stuntguard.data.response.recommendation.FoodRecomResponse
import com.example.stuntguard.data.response.recommendation.PostRecomResponse
import com.example.stuntguard.data.response.recommendation.RecomByChildIDResponse
import com.example.stuntguard.repository.Repository
import kotlinx.coroutines.launch

class FoodRecommendationViewModel(private val repository: Repository): ViewModel() {

    val foodRecom : LiveData<FoodRecomResponse> = repository.foodRecomData
    val postFoodRecom: LiveData<PostRecomResponse> = repository.postFoodRecom
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getUserData(): LiveData<UserModel> {
        return repository.getUser()
    }

    fun foodRecom(bearerToken: String, recommendation_id: String){
        viewModelScope.launch {
            repository.foodRecom(bearerToken, recommendation_id)
        }
    }

    fun postFoodRecom(bearerToken: String, predict_id: String){
        viewModelScope.launch {
            repository.postFoodRecom(bearerToken, predict_id)
        }
    }

}