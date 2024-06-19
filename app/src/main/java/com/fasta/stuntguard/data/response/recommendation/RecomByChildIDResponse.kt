package com.example.stuntguard.data.response.recommendation

import com.google.gson.annotations.SerializedName

data class RecomByChildIDResponse(

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem>
)

data class RecommendationsItem(

	@field:SerializedName("child_id")
	val childId: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("predict_id")
	val predictId: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("recommendation_id")
	val recommendationId: String
)
