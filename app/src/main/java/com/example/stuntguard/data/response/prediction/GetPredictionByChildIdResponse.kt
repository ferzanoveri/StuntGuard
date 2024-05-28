package com.example.stuntguard.data.response.prediction

import com.google.gson.annotations.SerializedName

data class GetPredictionByChildIdResponse(

    @field:SerializedName("data")
	val data: ArrayList<ChildPrediction>,

    @field:SerializedName("message")
	val message: String,

    @field:SerializedName("status")
	val status: Boolean
)

data class ChildPrediction(

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
	val predictResult: Int,

	@field:SerializedName("breastfeeding")
	val breastfeeding: Int,

	@field:SerializedName("child_weight")
	val childWeight: Any,

	@field:SerializedName("child_height")
	val childHeight: Any,

	@field:SerializedName("energy")
	val energy: Any
)
