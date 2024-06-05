package com.example.stuntguard.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.fasta.stuntguard.data.api.ApiConfig
import com.fasta.stuntguard.data.model.UserModel
import com.fasta.stuntguard.data.response.LoginResponse
import com.fasta.stuntguard.data.response.RegisterResponse
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError : LiveData<Boolean> = _isError

    fun postRegister(parentName: String, email: String, phone: String, password: String, confirmPassword: String){
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().postRegister(parentName, email, phone, password, confirmPassword)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null){
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

//    fun postForum(token: String, image: MultipartBody.Part, title: RequestBody, description: RequestBody){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().postForum(token, image, title, description)
//        client.enqueue(object : Callback<UploadForumResponse> {
//            override fun onResponse(
//                call: Call<UploadForumResponse>,
//                response: Response<UploadForumResponse>
//            ) {
//                _isLoading.value = true
//                if (response.isSuccessful && response.body() != null){
//                    _isLoading.value = false
//                    _postForumData.value = response.body()
//                } else {
//                    _isLoading.value = false
//                }
//
//            }
//
//            override fun onFailure(call: Call<UploadForumResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//
//        })
//    }
//
//    fun getAllForum(){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getForums()
//        client.enqueue(object : Callback<GetAllForumResponse> {
//            override fun onResponse(
//                call: Call<GetAllForumResponse>,
//                response: Response<GetAllForumResponse>
//            ) {
//                _isLoading.value = true
//                if (response.isSuccessful && response.body() != null){
//                    _isLoading.value = false
//                    _allForums.value = response.body()
//                } else {
//                    _isLoading.value = false
//                }
//            }
//
//            override fun onFailure(call: Call<GetAllForumResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//
//        })
//    }
//
//    fun getDetailForum(id:String){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getDetailForum(id)
//        client.enqueue(object : Callback<GetDetailForumResponse> {
//            override fun onResponse(
//                call: Call<GetDetailForumResponse>,
//                response: Response<GetDetailForumResponse>
//            ) {
//                _isLoading.value = true
//                if (response.isSuccessful && response.body() != null){
//                    _isLoading.value = false
//                    _detailForum.value = response.body()
//                } else {
//                    _isLoading.value = false
//                }
//            }
//
//            override fun onFailure(call: Call<GetDetailForumResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e("GET_DETAIL", "onFailure: ${t.message.toString()}")
//            }
//
//        })
//    }
//
//    fun postComment(token: String, id: String, comment: String){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().postComment(token, id, comment)
//        client.enqueue(object : Callback<PostCommentResponse> {
//            override fun onResponse(
//                call: Call<PostCommentResponse>,
//                response: Response<PostCommentResponse>
//            ) {
//                _isLoading.value = true
//                if (response.isSuccessful && response.body() != null){
//                    _isLoading.value = false
//                    _postCommentData.value = response.body()
//                } else {
//                    _isLoading.value = false
//                }
//            }
//
//            override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//
//        })
//    }
//
//    fun getDetailBlog(id: String){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getDetailBlog(id)
//        client.enqueue(object : Callback<GetDetailBlogResponse> {
//            override fun onResponse(
//                call: Call<GetDetailBlogResponse>,
//                response: Response<GetDetailBlogResponse>
//            ) {
//                _isLoading.value = true
//                if (response.isSuccessful && response.body() != null){
//                    _isLoading.value = false
//                    _detailBlog.value = response.body()
//                } else {
//                    _isLoading.value = false
//                }
//            }
//
//            override fun onFailure(call: Call<GetDetailBlogResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//
//        })
//    }
//
//    fun getAllBlogs(){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getBlogs()
//        client.enqueue(object : Callback<GetAllBlogResponse> {
//            override fun onResponse(
//                call: Call<GetAllBlogResponse>,
//                response: Response<GetAllBlogResponse>
//            ) {
//                _isLoading.value = true
//                if (response.isSuccessful && response.body() != null){
//                    _isLoading.value = false
//                    _allBlogs.value = response.body()
//                } else {
//                    _isLoading.value = false
//                }
//            }
//
//            override fun onFailure(call: Call<GetAllBlogResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//
//        })
//    }
//
//    fun getAllMenu(){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getAllMenu()
//        client.enqueue(object : Callback<GetAllMenu> {
//            override fun onResponse(call: Call<GetAllMenu>, response: Response<GetAllMenu>) {
//                _isLoading.value = true
//                if (response.isSuccessful && response.body() != null){
//                    _isLoading.value = false
//                    _getAllMenuData.value = response.body()
//                } else {
//                    _isLoading.value = false
//                }
//            }
//
//            override fun onFailure(call: Call<GetAllMenu>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//
//        })
//    }
//
//    fun getDetailMenu(id: String){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getDetailMenu(id)
//        client.enqueue(object : Callback<DetailMenu> {
//            override fun onResponse(call: Call<DetailMenu>, response: Response<DetailMenu>) {
//                _isLoading.value = true
//                if (response.isSuccessful && response.body() != null){
//                    _isLoading.value = false
//                    _detailMenu.value = response.body()
//                } else {
//                    _isLoading.value = false
//                }
//            }
//
//            override fun onFailure(call: Call<DetailMenu>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//
//        })
//    }
//
//    fun predictImage(image: MultipartBody.Part){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().predictImage(image)
//        client.enqueue(object : Callback<PredictResponse> {
//            override fun onResponse(
//                call: Call<PredictResponse>,
//                response: Response<PredictResponse>
//            ) {
//                _isLoading.value = true
//                if (response.isSuccessful && response.body() != null){
//                    _isLoading.value = false
//                    _predictData.value = response.body()
//                } else {
//                    _isLoading.value = false
//                }
//            }
//
//            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
//                _isLoading.value = false
//                _isError.value = true
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//
//        })
//    }
//
    fun getUser() : LiveData<UserModel> {
        return preferences.getUser().asLiveData()
    }

    suspend fun saveUser(user: UserModel){
        preferences.saveUser(user)
    }

    suspend fun logout() {
        preferences.logout()
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

