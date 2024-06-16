package com.fasta.stuntguard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fasta.stuntguard.data.model.UserModel
import com.fasta.stuntguard.data.response.GetAllNewsResponse
import com.fasta.stuntguard.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel (private val repository: Repository) : ViewModel(){
    val allNews : LiveData<GetAllNewsResponse> = repository.allNews
    val allNewsLatest: LiveData<GetAllNewsResponse> = repository.allNewsLates
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getUserData(): LiveData<UserModel> {
        return repository.getUser()
    }

    fun getAllNewsRelevansi(){
        viewModelScope.launch {
            repository.getAllNewsRelevansi()
        }
    }

    fun getAllNewsLatest(){
        viewModelScope.launch {
            repository.getAllNewsLatest()
        }
    }

}