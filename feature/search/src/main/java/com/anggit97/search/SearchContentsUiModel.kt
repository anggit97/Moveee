package com.anggit97.search

import com.anggit97.model.model.Movie

data class SearchContentsUiModel(
    val movies: List<Movie>,
    val hasNoItem: Boolean
)
