package com.am.taskarticles.model

import com.google.gson.annotations.SerializedName

data class ResponseBase(
    val status: String,
    val copyright: String,
    @SerializedName("num_results") val numResults: Int,
    val results: List<Article>
)