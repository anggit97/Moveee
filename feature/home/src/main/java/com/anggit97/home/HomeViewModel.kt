package com.anggit97.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anggit97.core.ext.postValueIfNew
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _headerUiModel = MutableLiveData<HomeHeaderUiModel>()
    val headerUiModel: LiveData<HomeHeaderUiModel>
        get() = _headerUiModel

    fun onNowTabClick() {
        _headerUiModel.postValueIfNew(HomeHeaderUiModel.Now)
    }

    fun onPlanTabClick() {
        _headerUiModel.postValueIfNew(HomeHeaderUiModel.Plan)
    }

    fun onFavouriteTabClick() {
        _headerUiModel.postValueIfNew(HomeHeaderUiModel.Favorite)
    }
}
