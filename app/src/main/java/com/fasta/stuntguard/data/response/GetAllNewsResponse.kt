package com.fasta.stuntguard.data.response

import com.google.gson.annotations.SerializedName

data class GetAllNewsResponse(
    @field:SerializedName("result")
    val result: ArrayList<News>,

    @field:SerializedName("hasPrevious")
    val hasPrevious: Boolean,

    @field:SerializedName("hasNext")
    val hasNext: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("currentPage")
    val currentPage: Int,

    @field:SerializedName("status")
    val status: Boolean
)

data class News(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("link")
    val link: String,

    @field:SerializedName("publisher")
    val publisher: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("token")
    val token: String
)
