package tech.orlov.data.network.dto

import com.google.gson.annotations.SerializedName

data class FilmsPageDto(
    @SerializedName("results") val films: List<FilmDto>?
)