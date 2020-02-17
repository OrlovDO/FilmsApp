package tech.orlov.data.db.mappers

import tech.orlov.data.db.dbo.FilmDbo
import tech.orlov.domain.entity.Film
import javax.inject.Inject

class FilmDboMapper @Inject constructor() {

    fun mapFilmToEntity(filmDbo: FilmDbo): Film {
        return Film(
            filmDbo.id,
            filmDbo.postersPath,
            filmDbo.title,
            filmDbo.overview
        )
    }

    fun mapFilmsListToEntity(filmsListDbo: List<FilmDbo>): List<Film> {
        return filmsListDbo.map(::mapFilmToEntity)
    }

    fun mapFilmToDbo(film: Film): FilmDbo {
        return FilmDbo(
            film.id,
            film.postersPath,
            film.title,
            film.overview
        )
    }

    fun mapFilmsListToDbo(filmsList: List<Film>): List<FilmDbo> {
        return filmsList.map(::mapFilmToDbo)
    }
}