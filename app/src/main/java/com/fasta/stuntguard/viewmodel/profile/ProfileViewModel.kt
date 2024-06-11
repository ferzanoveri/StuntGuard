package com.fasta.stuntguard.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fasta.stuntguard.data.model.UserModel
import com.fasta.stuntguard.data.response.ChangePasswordResponse
import com.fasta.stuntguard.data.response.Child
import com.fasta.stuntguard.data.response.ParentChildResponse
import com.fasta.stuntguard.data.response.PostChildResponse
import com.fasta.stuntguard.repository.Repository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    val updatePasswordResponse: LiveData<ChangePasswordResponse> = repository.updatePasswordResponse
    val parentChild: LiveData<ParentChildResponse> = repository.parentChild
    val postChildData: LiveData<PostChildResponse> = repository.postChildData
    val isLoading: LiveData<Boolean> = repository.isLoading
    val isError: LiveData<Boolean> = repository.isError

    fun getParentChild(id: String) {
        viewModelScope.launch {
            repository.getParentChild(id)
        }
    }

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

    fun updatePassword(parentId: String, oldPassword: String, newPassword: String, confirmPassword: String) {
        repository.updatePassword(parentId, oldPassword, newPassword, confirmPassword)
    }

    fun postChild(
        id: String,
        childName: String,
        childGender: String,
        birthDate: String,
        birthWeight: Double,
        birthHeight: Int,
        breastfeeding: String
    ) {
        viewModelScope.launch {
            repository.postChild(id, childName, childGender, birthDate, birthWeight, birthHeight, breastfeeding)
        }
    }

}