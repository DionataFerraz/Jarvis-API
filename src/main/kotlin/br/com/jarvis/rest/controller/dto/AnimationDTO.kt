package br.com.jarvis.rest.controller.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

data class AnimationDTO(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("releaseDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    val releaseDate: Date,
    @JsonProperty("completionDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    val completionDate: Date? = null,
    @JsonProperty("episodeQtd")
    val episodeQtd: Int,
    @JsonProperty("seasonQtd")
    val seasonQtd: Int,
    @JsonProperty("imagePath")
    val imagePath: String,
)