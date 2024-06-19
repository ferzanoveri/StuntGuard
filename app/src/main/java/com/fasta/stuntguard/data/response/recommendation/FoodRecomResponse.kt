package com.example.stuntguard.data.response.recommendation

import com.google.gson.annotations.SerializedName

data class FoodRecomResponse(

	@field:SerializedName("food_details")
	val foodDetails: List<FoodDetailsItem>
)

data class FoodDetailsItem(

	@field:SerializedName("carbohydrates")
	val carbohydrates: Any,

	@field:SerializedName("food_name")
	val foodName: String,

	@field:SerializedName("fiber")
	val fiber: Any,

	@field:SerializedName("potassium")
	val potassium: Any,

	@field:SerializedName("portion")
	val portion: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("recommendation_id")
	val recommendationId: String,

	@field:SerializedName("calories")
	val calories: Any,

	@field:SerializedName("food_id")
	val foodId: String,

	@field:SerializedName("saturated_fat")
	val saturatedFat: Any,

	@field:SerializedName("sodium")
	val sodium: Any,

	@field:SerializedName("unit")
	val unit: String,

	@field:SerializedName("monounsaturated_fat")
	val monounsaturatedFat: Any,

	@field:SerializedName("polyunsaturated_fat")
	val polyunsaturatedFat: Any,

	@field:SerializedName("protein")
	val protein: Any,

	@field:SerializedName("fat")
	val fat: Any,

	@field:SerializedName("cholesterol")
	val cholesterol: Any,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("sugar")
	val sugar: Any
)
