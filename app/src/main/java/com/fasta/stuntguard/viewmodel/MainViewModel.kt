package com.fasta.stuntguard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stuntguard.repository.Repository
import com.fasta.stuntguard.data.model.NewsModel
import com.fasta.stuntguard.data.model.UserModel
import kotlinx.coroutines.launch

class MainViewModel (private val repo: Repository) : ViewModel(){
    private val _newsList = MutableLiveData<List<NewsModel>>()
    val newsList: LiveData<List<NewsModel>> get() = _newsList


    fun getSession(): LiveData<UserModel> {
        return repo.getUser()
    }

    fun fetchHealthNews() {
        repo.getHealthNews(
            onSuccess = { newsList ->
                _newsList.postValue(newsList)
            },
            onFailure = { errorMessage ->
            }
        )
    }

}