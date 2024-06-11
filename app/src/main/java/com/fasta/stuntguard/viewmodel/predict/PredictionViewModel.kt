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
    val predictionResponse: LiveData<PredictionResponse> = repository.predictionResponse
    val children: LiveData<ParentChildResponse> = repository.parentChild
    val parentChild: LiveData<ParentChildResponse> = repository.parentChild

    fun postPrediction(childId: String, childWeight: Float, childHeight: Float, breastfeeding: Boolean?) {
        repository.postPrediction(childId, childWeight, childHeight, breastfeeding)
    }

    fun getUserData(): LiveData<UserModel> {
        return repository.getUser()
    }

    fun getParentChild(id: String) {
        viewModelScope.launch {
            repository.getParentChild(id)
        }
    }
}