package br.com.jarvis.rest.controller.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

data class AuthorDTO(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("birthday")
    @JsonFormat(pattern="yyyy-MM-dd")
    val birthday: Date,
    @JsonProperty("synopsis")
    val synopsis: String,
    @JsonProperty("imagePath")
    val imagePath: String,
)