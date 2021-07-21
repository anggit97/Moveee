package com.anggit97.data.repository.internal.mapper

import com.anggit97.data.api.response.*
import com.anggit97.model.model.RequestToken
import com.anggit97.model.model.SessionId
import com.anggit97.model.model.Cast
import com.anggit97.model.model.*


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
fun MovieListResponse.toMovieList() = movies?.map { it.toMovie() } ?: emptyList()

private fun MovieResponse.toMovie() = Movie(
    adult = adult ?: false,
    backdropPath = backdropPath ?: "",
    genres = genreIds?.map { it.toString() }?.toList(),
    movieId = id ?: 0,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle ?: "",
    overview = overview ?: "",
    popularity = popularity,
    posterPath = posterPath ?: "",
    releaseDate = releaseDate ?: "",
    title = title ?: "",
    video = video ?: false,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0
)

fun MovieDetailResponse.toMovieDetail() = MovieDetail(
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
    emptyList()
)

fun CreditsListResponse.toCastList(): List<Cast> {
    return cast?.map { it.toCast() } ?: emptyList()
}

fun CastResponse.toCast() = Cast(
    adult,
    castId,
    character,
    creditId,
    gender,
    id,
    knownForDepartment,
    name,
    order,
    originalName,
    popularity,
    profilePath
)

fun MovieVideosResponse.toMovieVideos(): List<Video> {
    return results?.map {
        Video(
            it.id ?: "",
            it.iso31661 ?: "",
            it.iso6391 ?: "",
            it.key ?: "",
            it.name ?: "",
            it.site ?: "",
            it.size ?: 0,
            it.type ?: ""
        )
    } ?: emptyList()
}

fun List<SpokenLanguageResponse>.toSpokenLanguage(): List<SpokenLanguage> {
    return map { SpokenLanguage(it.englishName, it.iso6391, it.name) }
}

fun List<ProductionCountryResponse>.toProductionCountry(): List<ProductionCountry> {
    return map { ProductionCountry(it.iso31661, it.name) }
}

fun List<ProductionCompanyResponse>.toProductionCompany(): List<ProductionCompany> {
    return map { ProductionCompany(it.id, it.getLogoPathUrl(), it.name, it.originCountry) }
}

fun List<GenreResponse>.toGenre(): List<Genre> {
    return map { it.toGenre() }
}

fun GenreResponse.toGenre() = Genre(
    id ?: 0,
    name ?: ""
)

fun RequestTokenResponse.toRequestToken() = RequestToken(
    expiresAt ?: "",
    requestToken ?: "",
    success ?: false
)

fun CreateSessionIdResponse.toSessionId() = SessionId(
    sessionId ?: "",
    success ?: false
)

fun AccountResponse.toAccount() = Account(
    id?:0,
    iso6391?:"",
    iso31661?:"",
    name?:"",
    includeAdult?:false,
    username?:"",
    avatarResponse?.gravatarResponse?.hash?:"",
    avatarResponse?.tmdbResponse?.avatarPath?:""
)


