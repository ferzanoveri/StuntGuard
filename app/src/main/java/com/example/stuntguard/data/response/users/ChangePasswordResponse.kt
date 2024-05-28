package com.example.stuntguard.data.response.users

import com.example.stuntguard.data.response.auth.Data
import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(

    @field:SerializedName("data")
	val data: Data,

    @field:SerializedName("message")
	val message: String,

    @field:SerializedName("status")
	val status: Boolean
)

data class User(

	@field:SerializedName("parent_name")
	val parentName: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("parent_id")
	val parentId: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("email")
	val email: String
)
