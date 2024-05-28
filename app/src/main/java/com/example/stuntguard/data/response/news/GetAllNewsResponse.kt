package com.example.stuntguard.data.response.news

import com.google.gson.annotations.SerializedName

data class GetAllNewsResponse(

    @field:SerializedName("result")
	val result: ArrayList<News>,

    @field:SerializedName("message")
	val message: String,

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
