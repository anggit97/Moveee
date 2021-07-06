package com.anggit97.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anggit97.model.repository.MovieeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieeeRepository
): ViewModel(){

    private val _uiModel = MutableLiveData<SearchContentsUiModel>()
    val uiModel: LiveData<SearchContentsUiModel>
        get() = _uiModel

    fun searchFor(query: String) {
        viewModelScope.launch {
            val movies = withContext(Dispatchers.IO) {
                repository.searchMovie(query)
            }
            _uiModel.value = SearchContentsUiModel(
                movies = movies,
                hasNoItem = movies.isEmpty()
            )
        }
    }
}