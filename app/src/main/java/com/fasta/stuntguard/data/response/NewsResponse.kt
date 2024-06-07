package com.fasta.stuntguard.data.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class NewsResponse(
    val result: List<Result>,
    val status: Boolean? = null,
    val message: String? = null
)

data class Result(
    val url: String?,
    val urlToImage: String?,
    val title: String?,
    val date: String?,
    val publisher: String?
)
