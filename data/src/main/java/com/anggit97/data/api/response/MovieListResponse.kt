package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    @SerialName("dates")
    val datesResponse: DatesResponse? = DatesResponse("", ""),
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val movies: List<MovieResponse>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
) {

    val endOfPage = totalPages == page
}