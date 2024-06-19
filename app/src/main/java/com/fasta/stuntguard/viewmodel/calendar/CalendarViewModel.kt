package com.example.stuntguard.viewmodel.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stuntguard.data.model.UserModel
import com.example.stuntguard.data.response.children.GetDetailChildResponse
import com.example.stuntguard.data.response.children.ParentChildResponse
import com.example.stuntguard.data.response.prediction.GetPredictionByChildIdResponse
import com.example.stuntguard.repository.Repository
import kotlinx.coroutines.launch

class CalendarViewModel(private val repository: Repository) : ViewModel() {
    val parentChild: LiveData<ParentChildResponse> = repository.parentChild
    val predictByChildId: LiveData<GetPredictionByChildIdResponse> = repository.predictionByChildId
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getUserData(): LiveData<UserModel> {
        return repository.getUser()
    }

    fun getParentChild(id: String) {
        viewModelScope.launch {
            repository.getParentChild(id)
        }
    }

    fun getPredictionByChildId(id: String) {
        viewModelScope.launch {
            repository.getPredictionByChildId(id)
        }
    }

}