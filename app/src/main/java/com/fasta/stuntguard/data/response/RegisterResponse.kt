package com.fasta.stuntguard.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean,

	@field:SerializedName("token")
	val token: String
)

data class Data(

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
