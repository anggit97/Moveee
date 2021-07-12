package com.anggit97.home

import androidx.paging.PagingData
import com.anggit97.model.Movie

enum class HomeHeaderUiModel {
    Now, Plan, Favorite
}

class HomeContentsUiModel(
    val movies: PagingData<Movie>
)
