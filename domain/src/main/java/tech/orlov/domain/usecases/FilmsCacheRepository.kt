package tech.orlov.domain.usecases

import io.reactivex.Completable
import io.reactivex.Maybe
import tech.orlov.domain.entity.Film

interface FilmsCacheRepository {

    fun getAllCachedFilms(): Maybe<List<Film>>

    fun getCachedFilmById(id: Long): Maybe<Film>

    fun updateCache(filmsList: List<Film>): Completable

}