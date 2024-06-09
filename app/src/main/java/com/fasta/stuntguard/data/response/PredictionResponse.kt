package com.fasta.stuntguard.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictionResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("child_id")
    val childId: String,

    @SerializedName("child_weight")
    val childWeight: Float,

    @SerializedName("child_height")
    val childHeight: Float,

    @SerializedName("breastfeeding")
    val breastfeeding: Boolean?,

    @SerializedName("prediction")
    val prediction: String,

    @SerializedName("accuracy")
    val accuracy: Float
) : Parcelable
