package com.example.stuntguard.viewmodel.predict

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stuntguard.data.model.UserModel
import com.example.stuntguard.data.response.children.ParentChildResponse
import com.example.stuntguard.data.response.prediction.PostPredictionResponse
import com.example.stuntguard.repository.Repository
import kotlinx.coroutines.launch

class PredictViewModel(private val repository: Repository) : ViewModel() {

    val children: LiveData<ParentChildResponse> = repository.parentChild
    val predictionResponse: LiveData<PostPredictionResponse> = repository.postPredictionData

    fun getUserData(): LiveData<UserModel> {
        return repository.getUser()
    }

    fun resetPostPredictionData() {
        viewModelScope.launch {
            repository.resetPostPredictionData()
        }
    }

    fun getParentChild(bearerToken: String, id: String) {
        viewModelScope.launch {
            repository.getParentChild(bearerToken, id)
        }
    }

    fun postPrediction(bearerToken: String, childId: String, weight: Float, height: Float, breastfeeding: String?) {
        viewModelScope.launch {
            repository.postPrediction(bearerToken, childId, weight, height, breastfeeding)
        }
    }

}