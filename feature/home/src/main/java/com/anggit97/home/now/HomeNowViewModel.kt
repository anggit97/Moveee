package com.anggit97.home.now

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anggit97.home.HomeContentsUiModel
import com.anggit97.home.tab.HomeContentsViewModel
import com.anggit97.model.domain.movielist.MovieListUseCase
import com.anggit97.model.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 05,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltViewModel
class HomeNowViewModel @Inject constructor(
    private val useCase: MovieListUseCase
) : ViewModel(), HomeContentsViewModel {

    private val _isLoading = MutableLiveData(false)
    override val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isError = MutableLiveData(false)
    override val isError: LiveData<Boolean>
        get() = _isError

    private val _contentsUiModel = MutableLiveData<HomeContentsUiModel>()
    override val contentsUiModel: LiveData<HomeContentsUiModel>
        get() = _contentsUiModel

    init {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getNowMovieListPaging().distinctUntilChanged().collectLatest {
                _contentsUiModel.postValue(HomeContentsUiModel(it))
            }
        }
    }

    override fun fetchNowMovieList(): Flow<PagingData<Movie>>{
        return useCase.getNowMovieListPaging().cachedIn(viewModelScope)
    }

    override fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {}
    }
}