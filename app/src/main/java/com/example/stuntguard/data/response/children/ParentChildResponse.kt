package com.example.stuntguard.data.response.children

import com.google.gson.annotations.SerializedName

data class ParentChildResponse(

    @field:SerializedName("data")
    val data: ArrayList<Child>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Boolean
)

data class Child(

    @field:SerializedName("child_gender")
    val childGender: Boolean,

    @field:SerializedName("child_id")
    val childId: String,

    @field:SerializedName("child_age")
    val childAge: Int,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("parent_id")
    val parentId: String,

    @field:SerializedName("birth_date")
    val birthDate: String,

    @field:SerializedName("birth_weight")
    val birthWeight: Double,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("breastfeeding")
    val breastfeeding: Boolean,

    @field:SerializedName("child_name")
    val childName: String,

    @field:SerializedName("birth_height")
    val birthHeight: Int
)
