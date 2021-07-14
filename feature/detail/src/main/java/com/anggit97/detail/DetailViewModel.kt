package com.anggit97.detail

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anggit97.core.device.ImageUriProvider
import com.anggit97.core.ui.EventLiveData
import com.anggit97.core.ui.MutableEventLiveData
import com.anggit97.model.domain.moviedetail.MovieDetailUseCase
import com.anggit97.model.domain.moviefavourite.MovieFavouriteUseCase
import com.anggit97.model.model.Movie
import com.anggit97.model.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: MovieDetailUseCase,
    private val favouriteUseCase: MovieFavouriteUseCase,
    private val imageUriProvider: ImageUriProvider
) : ViewModel() {

    private lateinit var movie: Movie
    private var movieDetail: MovieDetail? = null

    private val _headerUiModel = MutableLiveData<HeaderUiModel>()
    val headerUiModel: LiveData<HeaderUiModel>
        get() = _headerUiModel

    private val _contentUiModel = MutableLiveData<ContentUiModel>()
    val contentUiModel: LiveData<ContentUiModel>
        get() = _contentUiModel

    private val _favoriteUiModel = MutableLiveData<Boolean>()
    val favoriteUiModel: LiveData<Boolean>
        get() = _favoriteUiModel

    private val _uiEvent = MutableEventLiveData<UiEvent>()
    val uiEvent: EventLiveData<UiEvent>
        get() = _uiEvent

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean>
        get() = _isError

    fun init(movie: Movie) {
        this.movie = movie
        _headerUiModel.value = HeaderUiModel(movie)
        viewModelScope.launch {
            _favoriteUiModel.postValue(favouriteUseCase.isFavoriteMovie(movie.movieId))
            val minDelay = async { delay(500) }
            val loadDetail = async { loadDetail(movie) }
            minDelay.await()

            movieDetail = loadDetail.await()?.also {
                renderDetail(it)
            }
        }
    }

    private suspend fun loadDetail(movie: Movie): MovieDetail? {
        _isError.postValue(false)
        try {
            return withContext(Dispatchers.IO) {
                useCase.getMovieById(movie.movieId.toString())
            }
        } catch (t: Throwable) {
            Timber.w(t)
            _isError.postValue(true)
        }
        return null
    }

    private suspend fun renderDetail(
        detail: MovieDetail
    ) = withContext(Dispatchers.Default) {
        _headerUiModel.postValue(
            HeaderUiModel(
                movie = movie,
                showTm = detail.runtime ?: 0,
                nations = detail.spoken_languages?.map { it.name ?: "-" }?.toList() ?: emptyList(),
                companies = detail.production_companies ?: emptyList(),
                genres = detail.genres?.map { it.name ?: "" } ?: emptyList()
            )
        )
        _contentUiModel.postValue(detail.toContentUiModel())
    }

    private fun MovieDetail.toContentUiModel(): ContentUiModel {
        val items = mutableListOf<ContentItemUiModel>()
        items.add(HeaderItemUiModel)
        items.add(
            CgvItemUiModel(
                movieId = id?.toString() ?: "",
                hasInfo = true,
                rating = vote_average.toString(),
                webLink = "https://google.com"
            )
        )
        items.add(
            LotteItemUiModel(
                movieId = id?.toString() ?: "",
                hasInfo = true,
                rating = vote_average.toString(),
                webLink = "https://google.com"
            )
        )
        items.add(
            MegaboxItemUiModel(
                movieId = id?.toString() ?: "",
                hasInfo = true,
                rating = vote_average.toString(),
                webLink = "https://google.com"
            )
        )

        items.add(
            NaverItemUiModel(
                rating = vote_average.toString(),
                webLink = "https://google.com"
            )
        )




        items.add(PlotItemUiModel(plot = overview ?: "-"))


        val persons = mutableListOf<PersonUiModel>()
        casts?.map {
            persons.add(
                PersonUiModel(
                    it.name ?: "-",
                    it.character ?: "-",
                    it.profile_path ?: "-",
                    it.original_name ?: "-"
                )
            )
        }
        if (persons.isNotEmpty()) {
            items.add(CastItemUiModel(persons = persons))
        }

        val trailers = videos.orEmpty()
        if (trailers.isNotEmpty()) {
            items.add(TrailerHeaderItemUiModel(movieTitle = title ?: "-"))
            items.addAll(
                trailers.map {
                    TrailerItemUiModel(trailer = it)
                }
            )
            items.add(TrailerFooterItemUiModel(movieTitle = title ?: "-"))
        }

        return ContentUiModel(items)
    }

    fun onFavoriteButtonClick(isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavorite) {
                favouriteUseCase.addFavoriteMovie(movie)
            } else {
                favouriteUseCase.removeFavoriteMovie(movie.movieId)
            }
            _favoriteUiModel.postValue(isFavorite)
        }
    }

    fun onRetryClick() {
        viewModelScope.launch {
            movieDetail = loadDetail(movie)?.also { renderDetail(it) }
        }
    }

    fun requestShareImage(target: ShareTarget, bitmap: Bitmap) {
        viewModelScope.launch {
            val uri = imageUriProvider(bitmap)
            _uiEvent.event = ShareAction(target, uri, "image/*")
        }
    }
}
