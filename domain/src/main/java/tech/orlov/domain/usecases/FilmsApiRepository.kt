package tech.orlov.domain.usecases

import io.reactivex.Single
import tech.orlov.domain.entity.Film

interface FilmsApiRepository {

    fun getAllFilms(): Single<List<Film>>

}