package com.anggit97.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anggit97.model.domain.moviesearch.MovieSearchUseCase
import com.anggit97.model.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: MovieSearchUseCase
) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Movie>>? = null

    suspend fun searchFor(query: String): Flow<PagingData<Movie>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = query
        val newResult = useCase.searchMovie(query).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}
