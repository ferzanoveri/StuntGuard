package com.fasta.stuntguard.viewmodel.profile.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fasta.stuntguard.data.model.UserModel
import com.fasta.stuntguard.data.response.ChangePasswordResponse
import com.fasta.stuntguard.repository.Repository

class ChangePasswordViewModel(private val repository: Repository) : ViewModel() {
    fun getUserData() : LiveData<UserModel> {
        return repository.getUser()
    }

    val updatePasswordResponse: LiveData<ChangePasswordResponse> = repository.updatePasswordResponse

    fun updatePassword(parentId: String, oldPassword: String, newPassword: String, confirmPassword: String) {
        repository.updatePassword(parentId, oldPassword, newPassword, confirmPassword)
    }
}