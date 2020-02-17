package tech.orlov.data.db

import android.text.format.DateUtils
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import tech.orlov.data.db.dbo.CacheTimestampDbo
import tech.orlov.data.db.mappers.FilmDboMapper
import tech.orlov.domain.entity.Film
import tech.orlov.domain.usecases.FilmsCacheRepository
import java.util.*
import javax.inject.Inject

class FilmsCacheDbRepository @Inject constructor(
    private val filmsCacheDao: FilmsCacheDao,
    private val filmDboMapper: FilmDboMapper
) : FilmsCacheRepository {

    companion object {
        private const val CACHE_TIME = DateUtils.HOUR_IN_MILLIS * 2
    }

    override fun getAllCachedFilms(): Maybe<List<Film>> {
        return getCahche(filmsCacheDao.getAllFilms())
            .subscribeOn(Schedulers.io())
            .map(filmDboMapper::mapFilmsListToEntity)
    }

    override fun getCachedFilmById(id: Long): Maybe<Film> {
        return getCahche(filmsCacheDao.getFilmById(id))
            .subscribeOn(Schedulers.io())
            .map(filmDboMapper::mapFilmToEntity)
    }

    override fun updateCache(filmsList: List<Film>): Completable {
        return clearCache()
            .subscribeOn(Schedulers.io())
            .andThen(filmsCacheDao.insertCacheTimestamp(
                CacheTimestampDbo(
                    Date()
                )
            ))
            .andThen(filmsCacheDao.insertAllFilms(filmDboMapper.mapFilmsListToDbo(filmsList)))
    }

    private fun <T> getCahche(checkableData: Maybe<T>): Maybe<T> {
        return filmsCacheDao.getCacheTimeStamp()
            .subscribeOn(Schedulers.io())
            .flatMap { getCacheIfValid(it, checkableData) }
    }

    private fun <T> getCacheIfValid(timestamp: CacheTimestampDbo, cacheSource: Maybe<T>): Maybe<T> {
        return if (timestamp.cacheTime.after(Date(System.currentTimeMillis() - CACHE_TIME))) {
            cacheSource
        } else {
            clearCache().toMaybe()
        }
    }

    private fun clearCache(): Completable {
        return filmsCacheDao.clearCacheTimestamp()
            .subscribeOn(Schedulers.io())
            .andThen(filmsCacheDao.clearFilmTable())
    }
}