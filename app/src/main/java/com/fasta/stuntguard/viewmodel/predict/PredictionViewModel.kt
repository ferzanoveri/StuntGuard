package com.fasta.stuntguard.viewmodel.predict

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fasta.stuntguard.data.response.PredictionResponse
import com.fasta.stuntguard.repository.Repository

class PredictionViewModel(private val repository: Repository) : ViewModel() {
    val predictionResponse: LiveData<PredictionResponse> = repository.predictionResponse
    val allPredictions: LiveData<List<PredictionResponse>> = repository.allPredictions
    val predictionsByChildId: LiveData<List<PredictionResponse>> = repository.predictionsByChildId

    fun postPrediction(childId: String, childWeight: Float, childHeight: Float, breastfeeding: Boolean?) {
        repository.postPrediction(childId, childWeight, childHeight, breastfeeding)
    }

    fun getAllPredictions() {
        repository.getAllPredictions()
    }

    fun getPredictionsByChildId(childId: String) {
        repository.getPredictionsByChildId(childId)
    }
}