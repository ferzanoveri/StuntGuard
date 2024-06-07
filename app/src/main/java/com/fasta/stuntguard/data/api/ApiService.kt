package com.fasta.stuntguard.data.api

import com.fasta.stuntguard.data.response.LoginResponse
import com.fasta.stuntguard.data.response.NewsResponse
import com.fasta.stuntguard.data.response.ParentChildResponse
import com.fasta.stuntguard.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("parentName") name: String,
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
    fun getNews(
        @Query("title") title: String,
        @Query("date") date: String,
        @Query("authorlabel") autorlabel: String,
        @Query("label") label: String,
        @Query("author") author: String,
        @Query("image") image: String,
        @Query("content") content: String,
        @Query("token") token: String
    ): Call<NewsResponse>
}