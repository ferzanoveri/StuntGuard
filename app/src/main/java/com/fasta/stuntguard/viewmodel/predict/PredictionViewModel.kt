package com.fasta.stuntguard.viewmodel.predict

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fasta.stuntguard.data.model.UserModel
import com.fasta.stuntguard.data.response.ParentChildResponse
import com.fasta.stuntguard.data.response.PredictionResponse
import com.fasta.stuntguard.repository.Repository
import kotlinx.coroutines.launch

class PredictionViewModel(private val repository: Repository) : ViewModel() {
    val children: LiveData<ParentChildResponse> = repository.parentChild
    val predictionResponse: LiveData<PredictionResponse> = repository.predictionResponse

    fun getUserData(): LiveData<UserModel> {
        return repository.getUser()
    }

    fun getParentChild(id: String) {
        viewModelScope.launch {
            repository.getParentChild(id)
        }
    }

    fun postPrediction(childId: String, weight: Float, height: Float, breastfeeding: Boolean?) {
        viewModelScope.launch {
            repository.postPrediction(childId, weight, height, breastfeeding)
        }
    }

}