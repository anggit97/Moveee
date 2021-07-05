package com.anggit97.home

import com.anggit97.model.Movie

enum class HomeHeaderUiModel {
    Now, Plan, Favorite
}

class HomeContentsUiModel(
    val movies: List<Movie>
)
