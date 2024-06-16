package com.fasta.stuntguard.data.api

import com.fasta.stuntguard.data.response.ChangePasswordResponse
import com.fasta.stuntguard.data.response.GetAllNewsResponse
import com.fasta.stuntguard.data.response.GetDetailNewsResponse
import com.fasta.stuntguard.data.response.LoginResponse
import com.fasta.stuntguard.data.response.ParentChildResponse
import com.fasta.stuntguard.data.response.RegisterResponse
import com.fasta.stuntguard.data.response.ChangeProfileResponse
import com.fasta.stuntguard.data.response.PostChildResponse
import com.fasta.stuntguard.data.response.PredictionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("parentName") parentName: String,
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

    @GET("news/relevansi")
    fun getAllNewsRelevansi() : Call<GetAllNewsResponse>

    @GET("news/latest")
    fun getAllNewsLatest() : Call<GetAllNewsResponse>

    @GET("news/{result_type}/{token}")
    fun getDetailNewsRelevansi(
        @Path("result_type") resultType: String,
        @Path("token") token: String
    ): Call<GetDetailNewsResponse>

    @GET("news/{result_type}/{token}")
    fun getDetailNewsLatest(
        @Path("result_type") resultType: String,
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


    @FormUrlEncoded
    @POST("predict/{child_id}")
    fun postPrediction(
        @Path("child_id") childId: String,
        @Field("child_weight") childWeight: Float,
        @Field("child_height") childHeight: Float,
        @Field("breastfeeding") breastfeeding: Boolean?
    ): Call<PredictionResponse>

    companion object {
        const val RESULT_TYPE_LATEST = "latest"
        const val RESULT_TYPE_RELEVANSI = "relevansi"
    }
}