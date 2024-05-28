package com.example.stuntguard.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stuntguard.data.model.UserModel
import com.example.stuntguard.data.response.users.ChangePasswordResponse
import com.example.stuntguard.data.response.users.ChangeProfileResponse
import com.example.stuntguard.data.response.users.GetDetailUserResponse
import com.example.stuntguard.data.response.auth.LoginResponse
import com.example.stuntguard.data.response.children.ParentChildResponse
import com.example.stuntguard.data.response.children.PostChildResponse
import com.example.stuntguard.repository.Repository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    val isLoading: LiveData<Boolean> = repository.isLoading
    val isError: LiveData<Boolean> = repository.isError
    val loginData : LiveData<LoginResponse> = repository.loginData
    val detailUser: LiveData<GetDetailUserResponse> = repository.detailUser
    val parentChild: LiveData<ParentChildResponse> = repository.parentChild
    val postChildData: LiveData<PostChildResponse> = repository.postChildData
    val updatePasswordResponse: LiveData<ChangePasswordResponse> = repository.updatePasswordResponse
    val updateProfileResponse: LiveData<ChangeProfileResponse> = repository.updateProfileResponse

    fun getParentChild(bearerToken: String, id: String) {
        viewModelScope.launch {
            repository.getParentChild(bearerToken, id)
        }
    }

    fun getUserData(): LiveData<UserModel> {
        return repository.getUser()
    }

    fun getDetailUser(bearerToken: String, id: String) {
        viewModelScope.launch {
            repository.getDetailUser(bearerToken, id)
        }
    }

    fun postChild(
        bearerToken: String,
        id: String,
        childName: String,
        childGender: String,
        birthDate: String,
        birthWeight: Double,
        birthHeight: Int,
        breastfeeding: String
    ) {
        viewModelScope.launch {
            repository.postChild(bearerToken, id, childName, childGender, birthDate, birthWeight, birthHeight, breastfeeding)
        }
    }

    fun resetPostChildData() {
        viewModelScope.launch {
            repository.resetPostChildData()
        }
    }
    fun resetLoginData() {
        viewModelScope.launch {
            repository.resetLoginData()
        }
    }

    fun updateProfile(bearerToken: String, parentId: String, parentName: String, email: String, phone: String?) {
        viewModelScope.launch {
            repository.updateProfile(bearerToken,parentId, parentName, email, phone)
        }
    }

    fun updatePassword(bearerToken: String, parentId: String, oldPassword: String, newPassword: String, confirmPassword: String) {
        repository.updatePassword(bearerToken, parentId, oldPassword, newPassword, confirmPassword)
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
}