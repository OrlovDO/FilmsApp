package tech.orlov.data

import android.app.Application
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tech.orlov.data.db.FilmCacheDatabase
import tech.orlov.data.db.FilmsCacheDbRepository
import tech.orlov.data.network.FilmsApi
import tech.orlov.data.network.FilmsApiNetworkRepository
import tech.orlov.data.settings.GlobalBuildSettings
import tech.orlov.domain.GlobalSettings
import tech.orlov.domain.usecases.FilmsApiRepository
import tech.orlov.domain.usecases.FilmsCacheRepository
import javax.inject.Singleton

@Module(includes = [DataModule.BindingsModule::class])
class DataModule {

    @Singleton
    @Provides
    fun provideFilmsApi(globalSettings: GlobalSettings): FilmsApi {
        return Retrofit.Builder()
            .baseUrl(globalSettings.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FilmsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCacheDatabase(application: Application): FilmCacheDatabase {
        return Room.databaseBuilder(
            application,
            FilmCacheDatabase::class.java,
            "film-cache-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFilmsCacheDao(filmCacheDatabase: FilmCacheDatabase): tech.orlov.data.db.FilmsCacheDao {
        return filmCacheDatabase.getFilmCacheDao()
    }

    @Module
    abstract class BindingsModule {
        @Binds
        abstract fun bindGlobalSettings(globalBuildSettings: GlobalBuildSettings): GlobalSettings

        @Binds
        abstract fun bindFilmsApiRepository(filmsApiNetworkRepository: FilmsApiNetworkRepository): FilmsApiRepository

        @Binds
        abstract fun bindFilmsCacheRepository(filmsCacheDbRepository: FilmsCacheDbRepository): FilmsCacheRepository
    }
}