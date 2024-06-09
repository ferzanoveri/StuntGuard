package com.fasta.stuntguard.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fasta.stuntguard.data.model.UserModel
import com.fasta.stuntguard.data.response.ChangePasswordResponse
import com.fasta.stuntguard.repository.Repository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    fun getUserData() : LiveData<UserModel> {
        return repository.getUser()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun updateProfile(parentId: String, parentName: String, email: String, phone: String?) {
        viewModelScope.launch {
            repository.updateProfile(parentId, parentName, email, phone)
        }
    }

}