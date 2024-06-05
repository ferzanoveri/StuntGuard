package com.fasta.stuntguard.data.response

data class ParentChildResponse(
	val data: List<DataItem>,
	val message: String,
	val status: Boolean
)

data class DataItem(
	val childGender: Boolean,
	val childId: String,
	val childAge: Int,
	val updatedAt: String,
	val parentId: String,
	val birthDate: String,
	val birthWeight: Any,
	val createdAt: String,
	val breastfeeding: Boolean,
	val childName: String,
	val birthHeight: Int
)

