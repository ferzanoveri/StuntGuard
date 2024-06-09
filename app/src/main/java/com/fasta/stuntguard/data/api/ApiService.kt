package com.fasta.stuntguard.data.api

import com.fasta.stuntguard.data.response.ChangePasswordResponse
import com.fasta.stuntguard.data.response.GetAllNewsResponse
import com.fasta.stuntguard.data.response.GetDetailNewsResponse
import com.fasta.stuntguard.data.response.LoginResponse
import com.fasta.stuntguard.data.response.ParentChildResponse
import com.fasta.stuntguard.data.response.RegisterResponse
import com.fasta.stuntguard.data.response.ChangeProfileResponse
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

    @FormUrlEncoded
    @GET("getParentChilds/{id}")
    fun getParentChild(
        @Path("id") id: String
    ): Call<ParentChildResponse>

    @GET("news")
    fun getNews() : Call<GetAllNewsResponse>

    @GET("news/{page}/{token}")
    fun getDetailNews(
        @Path("page") page: Int,
        @Path("token") token: String
    ) : Call<GetDetailNewsResponse>

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
}