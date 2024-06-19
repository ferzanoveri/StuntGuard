package com.example.stuntguard.data.api

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
import com.example.stuntguard.data.response.recommendation.PostRecomResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("parent_name") parentName: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>


    @GET("parent/childs/{id}")
    fun getParentChild(
        @Path("id") id: String
    ): Call<ParentChildResponse>

    @FormUrlEncoded
    @POST("child/{id}")
    fun postChild(
        @Path("id") id: String,
        @Field("child_name") childName: String,
        @Field("child_gender") childGender: String,
        @Field("birth_date") birthDate: String,
        @Field("birth_weight") birthWeight: Double,
        @Field("birth_height") birthHeight: Int,
        @Field("breastfeeding") breastfeeding: String
    ): Call<PostChildResponse>

    @GET("news/relevansi")
    fun getAllNewsRelevansi(): Call<GetAllNewsResponse>

    @GET("news/latest")
    fun getAllNewsLatest(): Call<GetAllNewsResponse>

    @GET("news/relevansi/{token}")
    fun getDetailNewsRelevansi(
        @Path("token") token: String
    ): Call<GetDetailNewsResponse>

    @GET("news/latest/{token}")
    fun getDetailNewsLatest(
        @Path("token") token: String
    ): Call<GetDetailNewsResponse>

    @FormUrlEncoded
    @PUT("user/updatePassword/{parent_id}")
    fun updatePassword(
        @Path("parent_id") parentId: String,
        @Field("oldPassword") oldPassword: String,
        @Field("newPassword") newPassword: String,
        @Field("confirmPassword") confirmPassword: String
    ): Call<ChangePasswordResponse>

    @FormUrlEncoded
    @PUT("user/update/{parent_id}")
    fun updateProfile(
        @Path("parent_id") parentId: String,
        @Field("parent_name") parentName: String,
        @Field("email") email: String,
        @Field("phone") phone: String?
    ): Call<ChangeProfileResponse>

    @GET("user/id/{id}")
    fun getDetailUser(
        @Path("id") id: String
    ): Call<GetDetailUserResponse>

    @GET("child/id/{id}")
    fun getDetailChild(
        @Path("id") id: String
    ): Call<GetDetailChildResponse>

    @FormUrlEncoded
    @POST("predict/{child_id}")
    fun postPredict(
        @Path("child_id") childId: String,
        @Field("child_weight") childWeight: Float,
        @Field("child_height") childHeight: Float,
        @Field("breastfeeding") breastfeeding: String?
    ): Call<PostPredictionResponse>

    @GET("predict/child/{child_id}")
    fun getPredictByChildId(
        @Path("child_id") childId: String
    ) : Call<GetPredictionByChildIdResponse>

    @POST("recom/{predict_id}")
    fun postFoodRecom(
        @Path("predict_id") predictId: String
    ): Call<PostRecomResponse>

    @GET("recom/child/{child_id}")
    fun getRecomByChildId(
        @Path("id") id: String
    ): Call<RecomByChildIDResponse>

    @GET("recom/id/{recommendation_id}/foods")
    fun foodRecom(
        @Path("recommendation_id") recommendationId: String
    ): Call<FoodRecomResponse>
}