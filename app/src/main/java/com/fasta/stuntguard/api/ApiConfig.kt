package com.fasta.stuntguard.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = "https://newsapi.org/v2/"
//    private const val API_KEY = "b4d8bbbffb0e45af991afa94c8a42f81"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val newsApiService: ApiService = retrofit.create(ApiService::class.java)
}