package com.fasta.stuntguard.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stuntguard.repository.Repository
import com.fasta.stuntguard.data.model.UserModel
import com.fasta.stuntguard.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: Repository) : ViewModel() {

    val loginData : LiveData<LoginResponse> = repository.loginData
    val isLoading: LiveData<Boolean> = repository.isLoading
    val isError: LiveData<Boolean> = repository.isError

    fun postLogin(email: String, password: String){
        viewModelScope.launch {
            repository.postLogin(email, password)
        }
    }

    fun saveUser(user: UserModel){
        viewModelScope.launch {
            repository.saveUser(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            repository.postLogin(email, password)
            val loginResponse = loginData.value
            if (loginResponse != null) {
                if (loginResponse.status) {
                    val user = UserModel(
                        name = loginResponse.data,
                        token = loginResponse.token,
                        userId = loginResponse.userId,
                        isLogin = true
                    )
                    saveUser(user)
                    onSuccess()
                } else {
                    onError(loginResponse.message)
                }
            } else {
                onError("Login failed")
            }
        }
    }
}