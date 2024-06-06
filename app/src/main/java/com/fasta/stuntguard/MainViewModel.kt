package com.fasta.stuntguard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stuntguard.repository.Repository
import com.fasta.stuntguard.data.model.UserModel
import kotlinx.coroutines.launch

class MainViewModel (private val repo: Repository) : ViewModel(){


    fun getSession(): LiveData<UserModel> {
        return repo.getUser()
    }

}