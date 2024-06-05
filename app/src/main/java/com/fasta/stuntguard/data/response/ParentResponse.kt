package com.fasta.stuntguard.data.response

data class ParentResponse(
	val data: Parent,
	val message: String,
	val status: Boolean
)

data class Parent(
	val parentName: String,
	val password: String,
	val updatedAt: String,
	val phone: String,
	val parentId: String,
	val createdAt: String,
	val email: String
)

