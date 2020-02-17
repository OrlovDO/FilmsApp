package tech.orlov.domain.entity

data class Film (
    var id: Long,
    val postersPath: String,
    val title: String,
    val overview: String
)