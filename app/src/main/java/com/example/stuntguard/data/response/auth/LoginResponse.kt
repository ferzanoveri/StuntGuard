package com.example.stuntguard.data.response.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("status")
	val status: Boolean,

	@field:SerializedName("token")
	val token: String
)
