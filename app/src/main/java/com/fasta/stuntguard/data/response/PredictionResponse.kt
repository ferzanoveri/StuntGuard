package com.fasta.stuntguard.data.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
	@SerializedName("child_id")
	val childId: String,

	@SerializedName("updated_at")
	val updatedAt: String,

	@SerializedName("predict_id")
	val predictId: String,

	@SerializedName("protein")
	val protein: Any,

	@SerializedName("created_at")
	val createdAt: String,

	@SerializedName("predict_result")
	val predictResult: String,

	@SerializedName("breastfeeding")
	val breastfeeding: String,

	@SerializedName("child_weight")
	val childWeight: Float,  // Assuming the correct type is Float

	@SerializedName("child_height")
	val childHeight: Float,  // Assuming the correct type is Float

	@SerializedName("energy")
	val energy: Any
)

