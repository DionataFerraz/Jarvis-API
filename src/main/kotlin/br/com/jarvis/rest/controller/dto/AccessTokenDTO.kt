package br.com.jarvis.rest.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenDTO(
    @JsonProperty("access_token")
    val accessToken: String,
)