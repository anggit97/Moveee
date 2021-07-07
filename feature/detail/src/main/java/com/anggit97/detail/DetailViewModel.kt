package com.anggit97.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anggit97.core.ui.EventLiveData
import com.anggit97.core.ui.MutableEventLiveData
import com.anggit97.model.Movie
import com.anggit97.model.MovieDetail
import com.anggit97.model.Video
import com.anggit97.model.repository.MovieeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieeeRepository,
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
            _favoriteUiModel.postValue(repository.isFavoriteMovie(movie.id))
            val minDelay = async { delay(500) }
            val loadDetail = async {
                loadDetail(movie)
            }
            val loadVideos = async {
                loadVideo(movie)
            }
            val videos = loadVideos.await()
            minDelay.await()
            movieDetail = loadDetail.await()?.also {
                it.videos = videos
                renderDetail(it)
            }
        }
    }

    private suspend fun loadVideo(movie: Movie): List<Video>? {
        _isError.postValue(false)
        try {
            return withContext(Dispatchers.IO) {
                repository.getMovieVideos(movie.id.toString())
            }
        } catch (t: Throwable) {
            Timber.w(t)
            _isError.postValue(true)
        }
        return null
    }

    private suspend fun loadDetail(movie: Movie): MovieDetail? {
        _isError.postValue(false)
        try {
            return withContext(Dispatchers.IO) {
                repository.getMovieById(movie.id.toString())
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
        persons.addAll(
            listOf(
                PersonUiModel(
                    name = "Nagato Uchia",
                    cast = "Samsul",
                    query = "Ujang"
                )
            )
        )
        persons.addAll(
            listOf(
                PersonUiModel(
                    name = "Hambali",
                    cast = "Imran",
                    query = "Nani Corre"
                )
            )
        )
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
                repository.addFavoriteMovie(movie)
            } else {
                repository.removeFavoriteMovie(movie.id)
            }
            _favoriteUiModel.postValue(isFavorite)
        }
    }

    fun onRetryClick() {
        viewModelScope.launch {
            movieDetail = loadDetail(movie)?.also { renderDetail(it) }
        }
    }
}
