package com.fasta.stuntguard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fasta.stuntguard.data.model.UserModel
import com.fasta.stuntguard.data.response.GetAllNewsResponse
import com.fasta.stuntguard.data.response.GetDetailNewsResponse
import com.fasta.stuntguard.repository.Repository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: Repository) : ViewModel() {
    val allNews: LiveData<GetAllNewsResponse> = repository.allNews
    val detailNews: LiveData<GetDetailNewsResponse> = repository.detailNews
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getUserData(): LiveData<UserModel> {
        return repository.getUser()
    }

    fun getAllNews(){
        viewModelScope.launch {
            repository.getAllNews()
        }
    }

    fun getDetailNews(resultType: String ,token: String){
        repository.getDetailNews("relevansi", token)
    }

    fun getRelevantNews(resultType: String ,token: String) {
        repository.getDetailNews("relevansi", token)
    }
}