package com.example.stuntguard.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stuntguard.data.response.auth.RegisterResponse
import com.example.stuntguard.repository.Repository
import kotlinx.coroutines.launch

class RegisterViewModel (private val repository: Repository) : ViewModel(){

    val registerData : LiveData<RegisterResponse> = repository.registerData
    val isLoading : LiveData<Boolean> = repository.isLoading
    val isError: LiveData<Boolean> = repository.isError

    fun postRegister(parentName: String, email: String, phone: String, password: String, confirmPassword: String){
        viewModelScope.launch {
            repository.postRegister(parentName, email, phone, password, confirmPassword)
        }
    }
}