package tech.orlov.data.network.dto

import com.google.gson.annotations.SerializedName

data class FilmDto(
    @SerializedName("id") val id: Long?,
    @SerializedName("poster_path") val postersPath: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("overview") val overview: String?
)