package com.anggit97.home.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.anggit97.home.HomeContentsUiModel
import com.anggit97.home.tab.HomeContentsViewModel
import com.anggit97.model.Movie
import com.anggit97.model.repository.MovieeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 05,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltViewModel
class HomePlanViewModel @Inject constructor(
    private val repository: MovieeeRepository
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
            updateList()
//            repository.getPlanMovieList()
//                .collect {
//                    _contentsUiModel.postValue(HomeContentsUiModel(it))
//                }
        }
    }

    override fun fetchNowMovieList(): Flow<PagingData<Movie>> {
        return emptyFlow()
    }

    override fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            updateList()
        }
    }

    private suspend fun updateList() {
        if (_isLoading.value == true) {
            return
        }
        _isLoading.postValue(true)
        try {
            repository.updatePlanMovieList()
            _isLoading.postValue(false)
            _isError.postValue(false)
        } catch (t: Throwable) {
            Timber.w(t)
            _isLoading.postValue(false)
            _isError.postValue(true)
        }
    }
}