package tech.orlov.domain.usecases

import io.reactivex.Single
import tech.orlov.domain.entity.Film
import javax.inject.Inject

class GetFilmByIdCommand @Inject constructor(
    private val filmsCacheRepository: FilmsCacheRepository,
    private val syncFilmsListCommand: SyncFilmsListCommand
){
    fun getFilmById(id: Long): Single<Film> {
        return filmsCacheRepository.getCachedFilmById(id)
            .switchIfEmpty(syncFilmsListCommand.syncFilms()
                .andThen(filmsCacheRepository.getCachedFilmById(id))
                .toSingle())
    }
}