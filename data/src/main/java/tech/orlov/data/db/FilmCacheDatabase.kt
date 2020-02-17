package tech.orlov.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tech.orlov.data.db.dbo.CacheTimestampDbo
import tech.orlov.data.db.dbo.FilmDbo

@Database(entities = arrayOf(CacheTimestampDbo::class, FilmDbo::class), version = 1)
abstract class FilmCacheDatabase : RoomDatabase() {

    abstract fun getFilmCacheDao(): FilmsCacheDao

}