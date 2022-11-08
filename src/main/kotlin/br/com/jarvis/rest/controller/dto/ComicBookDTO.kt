package br.com.jarvis.rest.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

class ComicBookDTO(
    @JsonProperty("comicType")
    val comicType: String,
    @JsonProperty("hasAnimation")
    val hasAnimation: Boolean,
    @JsonProperty("name")
    var name: String,
    @JsonProperty("description")
    var description: String,
    @JsonProperty("language")
    var language: String,
    @JsonProperty("releaseDate")
    var releaseDate: LocalDate,
    @JsonProperty("completionDate")
    var completionDate: LocalDate? = null
)
