package tech.orlov.domain.usecases

import io.reactivex.Completable
import io.reactivex.Single
import tech.orlov.domain.entity.Film
import javax.inject.Inject

class SyncFilmsListCommand @Inject constructor(
    private val filmsCacheRepository: FilmsCacheRepository,
    private val filmsApiRepository: FilmsApiRepository
) {

    fun syncFilms(): Completable {
        return filmsApiRepository.getAllFilms()
            .flatMapCompletable {
                filmsCacheRepository
                    .updateCache(it)
            }
    }
}