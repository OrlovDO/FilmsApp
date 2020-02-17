package tech.orlov.domain.usecases

import io.reactivex.Single
import tech.orlov.domain.entity.Film
import javax.inject.Inject

class GetAllFilmsCommand @Inject constructor(
    private val filmsCacheRepository: FilmsCacheRepository,
    private val syncFilmsListCommand: SyncFilmsListCommand
) {
    fun getAllFilms(): Single<List<Film>> {
        return filmsCacheRepository.getAllCachedFilms()
            .switchIfEmpty(syncFilmsListCommand.syncFilms()
                .andThen(filmsCacheRepository.getAllCachedFilms())
                .toSingle())
    }
}