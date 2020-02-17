package tech.orlov.data.network

import android.util.Log
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import tech.orlov.data.network.mappers.FilmDtoMapper
import tech.orlov.domain.entity.Film
import tech.orlov.domain.usecases.FilmsApiRepository
import javax.inject.Inject

class FilmsApiNetworkRepository @Inject constructor(
    private val filmsApi: FilmsApi,
    private val filmDtoMapper: FilmDtoMapper
) : FilmsApiRepository {

    override fun getAllFilms(): Single<List<Film>> {
        return filmsApi.getFilmsList()
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                Log.i("Net", it.toString())
            }
            .map { it.films }
            .map(filmDtoMapper::mapFilmsListToEntity)
    }

}