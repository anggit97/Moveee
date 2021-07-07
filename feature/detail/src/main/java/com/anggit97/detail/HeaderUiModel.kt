package com.anggit97.detail

import androidx.annotation.Keep
import com.anggit97.model.Movie
import com.anggit97.model.ProductionCompany
import com.anggit97.model.Video

@Keep
data class HeaderUiModel(
    val movie: Movie,
    val showTm: Int = 0,
    val nations: List<String> = emptyList(),
    val companies: List<ProductionCompany> = emptyList(),
    val genres: List<String> = emptyList()
)

@Keep
class ContentUiModel(
    val items: List<ContentItemUiModel>
)

@Keep
sealed class ContentItemUiModel

@Keep
object HeaderItemUiModel : ContentItemUiModel()

@Keep
class CgvItemUiModel(
    val movieId: String,
    val hasInfo: Boolean,
    val rating: String,
    val webLink: String?
) : ContentItemUiModel()

@Keep
class LotteItemUiModel(
    val movieId: String,
    val hasInfo: Boolean,
    val rating: String,
    val webLink: String?
) : ContentItemUiModel()

@Keep
class MegaboxItemUiModel(
    val movieId: String,
    val hasInfo: Boolean,
    val rating: String,
    val webLink: String?
) : ContentItemUiModel()

@Keep
class NaverItemUiModel(
    val rating: String,
    val webLink: String?
) : ContentItemUiModel()

@Keep
class BoxOfficeItemUiModel(
    val rank: Int,
    val rankDate: String,
    val audience: Int,
    val screenDays: Int,
    val rating: String,
    val webLink: String?
) : ContentItemUiModel()

@Keep
class ImdbItemUiModel(
    val imdb: String,
    val rottenTomatoes: String,
    val metascore: String,
    val webLink: String?
) : ContentItemUiModel()

@Keep
class PlotItemUiModel(
    val plot: String
) : ContentItemUiModel() {

    var isExpanded: Boolean = false
}


@Keep
class TrailerItemUiModel(
    val trailer: Video
) : ContentItemUiModel()

@Keep
class CastItemUiModel(
    val persons: List<PersonUiModel>
) : ContentItemUiModel()

@Keep
class PersonUiModel(
    val name: String,
    val cast: String,
    val avatar: String,
    val query: String
)

@Keep
class TrailerHeaderItemUiModel(
    val movieTitle: String
) : ContentItemUiModel()

@Keep
class TrailerFooterItemUiModel(
    val movieTitle: String
) : ContentItemUiModel()

val ContentItemUiModel.id: String
    get() = when (this) {
        HeaderItemUiModel -> "header"
        is CgvItemUiModel -> "cgv"
        is LotteItemUiModel -> "lotte"
        is MegaboxItemUiModel -> "megabox"
        is NaverItemUiModel -> "naver"
        is BoxOfficeItemUiModel -> "boxoffice"
        is ImdbItemUiModel -> "imdb"
        is PlotItemUiModel -> "plot"
        is CastItemUiModel -> "cast"
        is TrailerHeaderItemUiModel -> "t_header"
        is TrailerFooterItemUiModel -> "t_footer"
        is TrailerItemUiModel -> "t_${trailer.key}"
    }

