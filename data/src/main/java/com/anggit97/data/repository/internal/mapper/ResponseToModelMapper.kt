package com.anggit97.data.repository.internal.mapper

import com.anggit97.data.api.response.*
import com.anggit97.model.Genre
import com.anggit97.model.Movie
import com.anggit97.model.MovieDetail
import com.anggit97.model.ProductionCompany
import com.anggit97.model.ProductionCountry
import com.anggit97.model.SpokenLanguage


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
internal fun MovieListResponse.toMovieList() = movies?.map { it.toMovie() } ?: emptyList()

private fun MovieResponse.toMovie() = Movie(
    adult ?: false,
    backdropPath ?: "",
    genreIds?.map { it.toString() }?.toList(),
    id ?: 0,
    originalLanguage,
    originalTitle ?: "",
    overview ?: "",
    popularity,
    posterPath ?: "",
    releaseDate ?: "",
    title ?: "",
    video ?: false,
    voteAverage ?: 0.0,
    voteCount ?: 0
)

internal fun MovieDetailResponse.toMovieDetail() = MovieDetail(
    adult,
    backdropPath,
    budget,
    genres?.toGenre() ?: emptyList(),
    homepage,
    id,
    imdbId,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    productionCompanies?.toProductionCompany() ?: emptyList(),
    productionCountries?.toProductionCountry() ?: emptyList(),
    releaseDate,
    revenue,
    runtime,
    spokenLanguages?.toSpokenLanguage() ?: emptyList(),
    status,
    tagline,
    title,
    video,
    voteAverage,
    voteCount,
)

internal fun List<SpokenLanguageResponse>.toSpokenLanguage(): List<SpokenLanguage> {
    return map { SpokenLanguage(it.englishName, it.iso6391, it.name) }
}

internal fun List<ProductionCountryResponse>.toProductionCountry(): List<ProductionCountry> {
    return map { ProductionCountry(it.iso31661, it.name) }
}

internal fun List<ProductionCompanyResponse>.toProductionCompany(): List<ProductionCompany> {
    return map { ProductionCompany(it.id, it.getLogoPathUrl(), it.name, it.originCountry) }
}

internal fun List<GenreResponse>.toGenre(): List<Genre> {
    return map { it.toGenre() }
}

internal fun GenreResponse.toGenre() = Genre(
    id ?: 0,
    name ?: ""
)