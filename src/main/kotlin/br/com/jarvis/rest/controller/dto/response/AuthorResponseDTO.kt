package br.com.jarvis.rest.controller.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

data class AuthorResponseDTO(
    val name: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthday: Date,
    val synopsis: String,
    val imagePath: String,
)