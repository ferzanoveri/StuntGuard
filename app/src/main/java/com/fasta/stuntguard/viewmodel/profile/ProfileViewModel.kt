package com.fasta.stuntguard.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.stuntguard.repository.Repository
import com.fasta.stuntguard.data.model.UserModel

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    fun getUserData() : LiveData<UserModel> {
        return repository.getUser()
    }
}