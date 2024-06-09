package com.fasta.stuntguard.data.response

import com.google.gson.annotations.SerializedName

data class GetDetailNewsResponse(

	@field:SerializedName("result")
	val result: DetailNews,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class DetailNews(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("authorLabel")
	val authorLabel: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String
)
