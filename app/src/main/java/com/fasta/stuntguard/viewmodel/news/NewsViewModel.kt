package com.example.stuntguard.viewmodel.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stuntguard.data.model.UserModel
import com.example.stuntguard.data.response.news.GetAllNewsResponse
import com.example.stuntguard.data.response.news.GetDetailNewsResponse
import com.example.stuntguard.repository.Repository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: Repository) : ViewModel() {

    val allNews: LiveData<GetAllNewsResponse> = repository.allNews
    val detailNews: LiveData<GetDetailNewsResponse> = repository.detailNews
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getDetailNews(tokenNews: String) {
        viewModelScope.launch {
            repository.getDetailNews(tokenNews)
        }
    }

    fun getUserData(): LiveData<UserModel> {
        return repository.getUser()
    }

    fun getAllNews() {
        viewModelScope.launch {
            repository.getAllNewsLatest()
        }
    }
}