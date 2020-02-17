package tech.orlov.ui.formatters

import tech.orlov.domain.GlobalSettings
import tech.orlov.domain.entity.Film
import tech.orlov.ui.vo.FilmVo
import javax.inject.Inject

class FilmVoFormatter @Inject constructor(
    private val globalSettings: GlobalSettings
) {

    fun formatFilm(film: Film): FilmVo {
        return FilmVo(
            film.id,
            globalSettings.baseImageUrl + film.postersPath,
            film.title,
            film.overview
        )
    }

    fun formatFilmsList(films: List<Film>): List<FilmVo> {
        return films.map(::formatFilm)
    }
}