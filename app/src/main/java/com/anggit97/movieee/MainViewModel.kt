package com.anggit97.movieee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anggit97.model.Movie
import com.anggit97.model.repository.MovieeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MovieeeRepository
): ViewModel(){

    private val _contentsUiModel = MutableLiveData<List<Movie>>()
    val contentsUiModel: LiveData<List<Movie>>
        get() = _contentsUiModel

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNowMovieList()
            repository.getNowMovieList()
                .onStart { delay(200) }
                .collect {
                    _contentsUiModel.postValue(it)
                }
        }
    }
}