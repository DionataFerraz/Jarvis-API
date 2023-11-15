package br.com.jarvis.rest.controller.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

data class AuthorRequestDTO(
    val name: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthday: Date,
    val synopsis: String,
    val imagePath: String,
)