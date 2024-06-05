package com.fasta.stuntguard.data.response

data class LoginResponse(
	val data: String,
	val message: String,
	val userId: String,
	val status: Boolean,
	val token: String
)

