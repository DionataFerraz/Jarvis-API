package br.com.jarvis.rest.controller.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(
    val email: String,
    val password: String,
)

data class CreateUserRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
)

data class LoginOutput(
    val name: String,
    val email: String,
    val role: String,
)