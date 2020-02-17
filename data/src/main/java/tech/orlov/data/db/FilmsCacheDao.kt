package tech.orlov.data.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import tech.orlov.data.db.dbo.CacheTimestampDbo
import tech.orlov.data.db.dbo.FilmDbo


@Dao
interface FilmsCacheDao {

    @Query("SELECT * FROM filmdbo")
    fun getAllFilms(): Maybe<List<FilmDbo>>

    @Query("SELECT * FROM filmdbo WHERE id = :id")
    fun getFilmById(id: Long): Maybe<FilmDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFilms(film: List<FilmDbo>): Completable

    @Query("DELETE FROM filmdbo")
    fun clearFilmTable(): Completable

    @Query("SELECT * FROM cachetimestampdbo LIMIT 1")
    fun getCacheTimeStamp(): Maybe<CacheTimestampDbo>

    @Insert
    fun insertCacheTimestamp(cacheTimestampDbo: CacheTimestampDbo): Completable

    @Query("DELETE FROM cachetimestampdbo")
    fun clearCacheTimestamp(): Completable

}