package com.anggit97.home.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anggit97.home.HomeContentsUiModel
import com.anggit97.model.domain.moviefavourite.MovieFavouriteUseCase
import com.anggit97.model.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 06,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltViewModel
class HomeFavouriteViewModel @Inject constructor(
    private val useCase: MovieFavouriteUseCase
) : ViewModel() {

    private val _contentsUiModel = MutableLiveData<HomeContentsUiModel>()
    val contentsUiModel: LiveData<HomeContentsUiModel>
        get() = _contentsUiModel

    init {
        useCase.getFavoriteMovieList()
            .onEach {
                _contentsUiModel.postValue(HomeContentsUiModel(it))
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun fetchMovieFavourite(): Flow<PagingData<Movie>> = useCase.getFavoriteMovieList().cachedIn(viewModelScope)
}