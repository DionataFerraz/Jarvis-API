package br.com.jarvis.rest.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class ComicBookDTO(
    @JsonProperty("comicType")
    val comicType: String,
    @JsonProperty("hasAnimation")
    val hasAnimation: Boolean,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("language")
    val language: String,
    @JsonProperty("releaseDate")
    val releaseDate: LocalDate,
    @JsonProperty("completionDate")
    val completionDate: LocalDate? = null,
    @JsonProperty("volumes")
    val volumes: List<VolumeDTO>? = listOf(),
)

data class VolumeDTO(
    @JsonProperty("number")
    val number: Int,
    @JsonProperty("releaseYear")
    val releaseYear: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("isbn")
    val isbn: Long,
    @JsonProperty("pages")
    val pages: Int,
    @JsonProperty("bookCoverType")
    val bookCoverType: String,
)