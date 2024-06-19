package com.example.stuntguard.data.response.recommendation

import com.google.gson.annotations.SerializedName

data class PostRecomResponse(

	@field:SerializedName("recommendation")
	val recommendation: Recommendation,

	@field:SerializedName("food_details")
	val foodDetails: List<FoodDetails>
)

data class FoodDetails(

	@field:SerializedName("carbohydrates")
	val carbohydrates: Any,

	@field:SerializedName("food_name")
	val foodName: String,

	@field:SerializedName("fiber")
	val fiber: Any,

	@field:SerializedName("potassium")
	val potassium: Int,

	@field:SerializedName("portion")
	val portion: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("recommendation_id")
	val recommendationId: String,

	@field:SerializedName("calories")
	val calories: Int,

	@field:SerializedName("food_id")
	val foodId: String,

	@field:SerializedName("saturated_fat")
	val saturatedFat: Any,

	@field:SerializedName("sodium")
	val sodium: Int,

	@field:SerializedName("unit")
	val unit: String,

	@field:SerializedName("monounsaturated_fat")
	val monounsaturatedFat: Any,

	@field:SerializedName("polyunsaturated_fat")
	val polyunsaturatedFat: Any,

	@field:SerializedName("protein")
	val protein: Int,

	@field:SerializedName("fat")
	val fat: Any,

	@field:SerializedName("cholesterol")
	val cholesterol: Int,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("sugar")
	val sugar: Any
)

data class Recommendation(

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
