package br.com.jarvis.rest.controller.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

data class AnimationResponseDTO(
    val name: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val releaseDate: Date,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val completionDate: Date? = null,
    val episodeQtd: Int,
    val seasonQtd: Int,
    val imagePath: String,
)