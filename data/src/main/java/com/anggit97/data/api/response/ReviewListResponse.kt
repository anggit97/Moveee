package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewListResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<ReviewResponse>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)