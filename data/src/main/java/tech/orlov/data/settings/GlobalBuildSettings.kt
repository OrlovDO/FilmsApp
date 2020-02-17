package tech.orlov.data.settings

import tech.orlov.domain.GlobalSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalBuildSettings @Inject constructor(): GlobalSettings {
    override val baseUrl = "https://api.themoviedb.org"
    override val baseImageUrl = "https://image.tmdb.org/t/p/w500/"
}