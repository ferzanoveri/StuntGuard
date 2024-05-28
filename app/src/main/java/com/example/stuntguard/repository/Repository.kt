package com.example.stuntguard.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.stuntguard.data.api.ApiConfig
import com.example.stuntguard.data.model.UserModel
import com.example.stuntguard.data.response.users.ChangePasswordResponse
import com.example.stuntguard.data.response.users.ChangeProfileResponse
import com.example.stuntguard.data.response.recommendation.FoodRecomResponse
import com.example.stuntguard.data.response.news.GetAllNewsResponse
import com.example.stuntguard.data.response.children.GetDetailChildResponse
import com.example.stuntguard.data.response.news.GetDetailNewsResponse
import com.example.stuntguard.data.response.users.GetDetailUserResponse
import com.example.stuntguard.data.response.prediction.GetPredictionByChildIdResponse
import com.example.stuntguard.data.response.auth.LoginResponse
import com.example.stuntguard.data.response.children.ParentChildResponse
import com.example.stuntguard.data.response.children.PostChildResponse
import com.example.stuntguard.data.response.prediction.PostPredictionResponse
import com.example.stuntguard.data.response.recommendation.RecomByChildIDResponse
import com.example.stuntguard.data.response.auth.RegisterResponse
import com.example.stuntguard.data.response.children.PostChild
import com.example.stuntguard.data.response.recommendation.PostRecomResponse
import com.example.stuntguard.utils.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(
    private val preferences: UserPreferences
) {

    private val _registerData = MutableLiveData<RegisterResponse>()
    val registerData: LiveData<RegisterResponse> = _registerData

    private val _loginData = MutableLiveData<LoginResponse>()
    val loginData: LiveData<LoginResponse> = _loginData

    private val _postChildData = MutableLiveData<PostChildResponse>()
    val postChildData: LiveData<PostChildResponse> = _postChildData

    private val _postPredictionData = MutableLiveData<PostPredictionResponse>()
    val postPredictionData: LiveData<PostPredictionResponse> = _postPredictionData

    private val _predictionByChildId = MutableLiveData<GetPredictionByChildIdResponse>()
    val predictionByChildId: LiveData<GetPredictionByChildIdResponse> = _predictionByChildId

    private val _foodRecomData = MutableLiveData<FoodRecomResponse>()
    val foodRecomData: LiveData<FoodRecomResponse> = _foodRecomData

    private val _recomByChildId = MutableLiveData<RecomByChildIDResponse>()
    val recomByChildId: LiveData<RecomByChildIDResponse> = _recomByChildId

    private val _postFoodRecom = MutableLiveData<PostRecomResponse>()
    val postFoodRecom: LiveData<PostRecomResponse> = _postFoodRecom

    private val _parentChild = MutableLiveData<ParentChildResponse>()
    val parentChild: LiveData<ParentChildResponse> = _parentChild

    private val _allNews = MutableLiveData<GetAllNewsResponse>()
    val allNews: LiveData<GetAllNewsResponse> = _allNews

    private val _allNewsLatest = MutableLiveData<GetAllNewsResponse>()
    val allNewsLates: LiveData<GetAllNewsResponse> = _allNewsLatest

    private val _detailNews = MutableLiveData<GetDetailNewsResponse>()
    val detailNews: LiveData<GetDetailNewsResponse> = _detailNews

    private val _detailUser = MutableLiveData<GetDetailUserResponse>()
    val detailUser: LiveData<GetDetailUserResponse> = _detailUser

    private val _detailChild = MutableLiveData<GetDetailChildResponse>()
    val detailChild: LiveData<GetDetailChildResponse> = _detailChild

    private val _updatePasswordResponse = MutableLiveData<ChangePasswordResponse>()
    val updatePasswordResponse: LiveData<ChangePasswordResponse> = _updatePasswordResponse

    private val _updateProfileResponse = MutableLiveData<ChangeProfileResponse>()
    val updateProfileResponse: LiveData<ChangeProfileResponse> = _updateProfileResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError


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

    fun postLogin(email: String, password: String) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _loginData.value = response.body()
                } else {
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

    fun getParentChild(bearerToken: String, id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getParentChild("Bearer $bearerToken", id)
        client.enqueue(object : Callback<ParentChildResponse> {
            override fun onResponse(
                call: Call<ParentChildResponse>,
                response: Response<ParentChildResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _parentChild.value = response.body()
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ParentChildResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
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
        _isLoading.value = true
        val client = ApiConfig.getApiService().postChild(
            "Bearer $bearerToken",
            id,
            childName,
            childGender,
            birthDate,
            birthWeight,
            birthHeight,
            breastfeeding
        )
        client.enqueue(object : Callback<PostChildResponse> {
            override fun onResponse(
                call: Call<PostChildResponse>,
                response: Response<PostChildResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _postChildData.value = response.body()
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<PostChildResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun postPrediction(
        bearerToken: String,
        childId: String,
        childWeight: Float,
        childHeight: Float,
        breastfeeding: String?
    ) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService()
            .postPredict("Bearer $bearerToken", childId, childWeight, childHeight, breastfeeding)
        client.enqueue(object : Callback<PostPredictionResponse> {
            override fun onResponse(
                call: Call<PostPredictionResponse>,
                response: Response<PostPredictionResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _postPredictionData.value =
                        response.body() // Pastikan nilai predictionResponse diset dengan body respons yang benar
                } else {
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<PostPredictionResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getPredictionByChildId(bearerToken: String, childId: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getPredictByChildId("Bearer $bearerToken", childId)
        client.enqueue(object : Callback<GetPredictionByChildIdResponse> {
            override fun onResponse(
                call: Call<GetPredictionByChildIdResponse>,
                response: Response<GetPredictionByChildIdResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _predictionByChildId.value =
                        response.body() // Pastikan nilai predictionResponse diset dengan body respons yang benar
                } else {
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<GetPredictionByChildIdResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getAllNewsRelevansi(bearerToken: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllNewsRelevansi("Bearer $bearerToken")
        client.enqueue(object : Callback<GetAllNewsResponse> {
            override fun onResponse(
                call: Call<GetAllNewsResponse>,
                response: Response<GetAllNewsResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _allNews.value = response.body()
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<GetAllNewsResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getAllNewsLatest(bearerToken: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllNewsLatest("Bearer $bearerToken")
        client.enqueue(object : Callback<GetAllNewsResponse> {
            override fun onResponse(
                call: Call<GetAllNewsResponse>,
                response: Response<GetAllNewsResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _allNewsLatest.value = response.body()
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<GetAllNewsResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getDetailNews(bearerToken: String, tokenNews: String) {
        _isLoading.value = true
        val client =
            ApiConfig.getApiService().getDetailNewsRelevansi("Bearer $bearerToken", tokenNews)
        client.enqueue(object : Callback<GetDetailNewsResponse> {
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

    fun getDetailUser(bearerToken: String, id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser("Bearer $bearerToken", id)
        client.enqueue(object : Callback<GetDetailUserResponse> {
            override fun onResponse(
                call: Call<GetDetailUserResponse>,
                response: Response<GetDetailUserResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _detailUser.value = response.body()
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<GetDetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getDetailChild(bearerToken: String, id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailChild("Bearer $bearerToken", id)
        client.enqueue(object : Callback<GetDetailChildResponse> {
            override fun onResponse(
                call: Call<GetDetailChildResponse>,
                response: Response<GetDetailChildResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _detailChild.value = response.body()
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<GetDetailChildResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun updatePassword(
        bearerToken: String,
        parentId: String,
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .updatePassword(
                "Bearer $bearerToken",
                parentId,
                oldPassword,
                newPassword,
                confirmPassword
            )
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
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun updateProfile(
        bearerToken: String,
        parentId: String,
        parentName: String,
        email: String,
        phone: String?
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .updateProfile("Bearer $bearerToken", parentId, parentName, email, phone)
        client.enqueue(object : Callback<ChangeProfileResponse> {
            override fun onResponse(
                call: Call<ChangeProfileResponse>,
                response: Response<ChangeProfileResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _updateProfileResponse.value = response.body()
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ChangeProfileResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun postFoodRecom(bearerToken: String, predict_id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postFoodRecom("Bearer $bearerToken", predict_id)
        client.enqueue(object : Callback<PostRecomResponse> {
            override fun onResponse(
                call: Call<PostRecomResponse>,
                response: Response<PostRecomResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _postFoodRecom.value = response.body()
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<PostRecomResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun foodRecom(bearerToken: String, recommendationId: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().foodRecom("Bearer $bearerToken", recommendationId)
        client.enqueue(object : Callback<FoodRecomResponse> {
            override fun onResponse(
                call: Call<FoodRecomResponse>,
                response: Response<FoodRecomResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _foodRecomData.value = response.body()
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<FoodRecomResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun recomByChildId(bearerToken: String, id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRecomByChildId("Bearer $bearerToken", id)
        client.enqueue(object : Callback<RecomByChildIDResponse> {
            override fun onResponse(
                call: Call<RecomByChildIDResponse>,
                response: Response<RecomByChildIDResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _recomByChildId.value = response.body()
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<RecomByChildIDResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun resetLoginData() {
        _loginData.value = LoginResponse("", "", "", false, "") // Atau nilai default lainnya
    }

    fun resetPostChildData() {
        _postChildData.value = PostChildResponse(
            PostChild(false, "", 0, "", "", "", "", "", false, "", 0),
            "",
            false
        ) // Atau nilai default lainnya
    }

    fun resetPostPredictionData() {
        _postPredictionData.value = PostPredictionResponse("", "", "", "", "", "", "", "", "", "")
    }

    fun getUser(): LiveData<UserModel> {
        return preferences.getUser().asLiveData()
    }

    suspend fun saveUser(user: UserModel) {
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
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(preferences)
            }.also { INSTANCE = it }
    }

}

