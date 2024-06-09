package com.fasta.stuntguard.utils.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fasta.stuntguard.repository.Repository
import com.fasta.stuntguard.viewmodel.MainViewModel
import com.fasta.stuntguard.utils.Injection
import com.fasta.stuntguard.viewmodel.NewsViewModel
import com.fasta.stuntguard.viewmodel.auth.LoginViewModel
import com.fasta.stuntguard.viewmodel.auth.RegisterViewModel
import com.fasta.stuntguard.viewmodel.profile.ProfileViewModel
import com.fasta.stuntguard.viewmodel.profile.changepassword.ChangePasswordViewModel

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> {
                ChangePasswordViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { INSTANCE = it }
    }
}
