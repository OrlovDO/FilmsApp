package tech.orlov.data.network.mappers

import tech.orlov.data.network.dto.FilmDto
import tech.orlov.domain.entity.Film
import javax.inject.Inject

class FilmDtoMapper @Inject constructor() {
    fun mapFilmToEntity(filmDto: FilmDto): Film {
        return Film(
            requireNotNull(filmDto.id),
            filmDto.postersPath ?: "",
            filmDto.title ?: "",
            filmDto.overview ?: ""
        )
    }

    fun mapFilmsListToEntity(filmsListDto: List<FilmDto>): List<Film> {
        return filmsListDto.map(::mapFilmToEntity)
    }
}