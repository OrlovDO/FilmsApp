package tech.orlov.data.network

import io.reactivex.Single
import retrofit2.http.GET
import tech.orlov.data.network.dto.FilmsPageDto

interface FilmsApi {

    @GET("3/movie/popular?api_key=befc7872520fd736c58948abb2f4a53c")
    fun getFilmsList(): Single<FilmsPageDto>

}