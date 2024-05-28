package com.example.stuntguard.data.response.prediction

import com.google.gson.annotations.SerializedName

data class PostPredictionResponse(

	@field:SerializedName("child_id")
	val childId: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("predict_id")
	val predictId: String,

	@field:SerializedName("protein")
	val protein: Any,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("predict_result")
	val predictResult: String,

	@field:SerializedName("breastfeeding")
	val breastfeeding: String,

	@field:SerializedName("child_weight")
	val childWeight: Any,

	@field:SerializedName("child_height")
	val childHeight: Any,

	@field:SerializedName("energy")
	val energy: Any
)
