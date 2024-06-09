package com.fasta.stuntguard.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.fasta.stuntguard.data.api.ApiConfig
import com.fasta.stuntguard.data.model.UserModel
import com.fasta.stuntguard.data.response.ChangePasswordResponse
import com.fasta.stuntguard.data.response.GetAllNewsResponse
import com.fasta.stuntguard.data.response.GetDetailNewsResponse
import com.fasta.stuntguard.data.response.LoginResponse
import com.fasta.stuntguard.data.response.RegisterResponse
import com.fasta.stuntguard.data.response.ChangeProfileResponse
import com.fasta.stuntguard.utils.UserPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(
    private val preferences: UserPreferences
){

    private val _registerData = MutableLiveData<RegisterResponse>()
    val registerData : LiveData<RegisterResponse> = _registerData

    private val _loginData = MutableLiveData<LoginResponse>()
    val loginData : LiveData<LoginResponse> = _loginData

    private val _allNews = MutableLiveData<GetAllNewsResponse>()
    val allNews: LiveData<GetAllNewsResponse> = _allNews

    private val _detailNews = MutableLiveData<GetDetailNewsResponse>()
    val detailNews: LiveData<GetDetailNewsResponse> = _detailNews

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError : LiveData<Boolean> = _isError

    private val _updatePasswordResponse = MutableLiveData<ChangePasswordResponse>()
    val updatePasswordResponse: LiveData<ChangePasswordResponse> = _updatePasswordResponse

    fun postRegister(
        parentName: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService()
            .postRegister(parentName, email, phone, password, confirmPassword)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _registerData.value = response.body()
                } else {
                    _isLoading.value = false
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun postLogin(email: String, password: String){
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null){
                    _isLoading.value = false
                    _loginData.value = response.body()
                }
                else {
                    _isLoading.value = false
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getUser() : LiveData<UserModel> {
        return preferences.getUser().asLiveData()
    }

    suspend fun saveUser(user: UserModel){
        preferences.saveUser(user)
    }

    suspend fun logout() {
        preferences.logout()
    }

    fun getAllNews(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getNews()
        client.enqueue(object : Callback<GetAllNewsResponse>{
            override fun onResponse(
                call: Call<GetAllNewsResponse>,
                response: Response<GetAllNewsResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null){
                    _isLoading.value = false
                    _allNews.value = response.body()
                }else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<GetAllNewsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getDetailNews(page: Int, token: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailNews(page, token)
        client.enqueue(object : Callback<GetDetailNewsResponse>{
            override fun onResponse(
                call: Call<GetDetailNewsResponse>,
                response: Response<GetDetailNewsResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _detailNews.value = response.body()
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<GetDetailNewsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun updatePassword(
        parentId: String,
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService()
            .updatePassword(parentId, oldPassword, newPassword, confirmPassword)
        client.enqueue(object : Callback<ChangePasswordResponse> {
            override fun onResponse(
                call: Call<ChangePasswordResponse>,
                response: Response<ChangePasswordResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _updatePasswordResponse.value = response.body()
                } else {
                    _isLoading.value = false
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private val _updateProfileResponse = MutableLiveData<ChangeProfileResponse>()
    val updateProfileResponse: LiveData<ChangeProfileResponse> = _updateProfileResponse

    fun updateProfile(parentId: String, parentName: String, email: String, phone: String?) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService()
            .updateProfile(parentId, parentName, email, phone)
        client.enqueue(object : Callback<ChangeProfileResponse> {
            override fun onResponse(
                call: Call<ChangeProfileResponse>,
                response: Response<ChangeProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _updateProfileResponse.value = response.body()
                } else {
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<ChangeProfileResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "Repository"

        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(preferences: UserPreferences): Repository =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Repository(preferences)
            }.also { INSTANCE = it }
    }

}

