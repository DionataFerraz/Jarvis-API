package br.com.jarvis.rest.controller.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

data class AnimationRequestDTO(
    val name: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val releaseDate: Date,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val completionDate: Date? = null,
    val episodeQtd: Int,
    val seasonQtd: Int,
    val imagePath: String,
)