package br.com.jarvis.service.auth

import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.rest.controller.auth.LoginCreateRequest
import br.com.jarvis.rest.controller.auth.LoginOutput

interface LoginService {
    fun createUser(loginCreateRequest: LoginCreateRequest): LoginOutput

    fun findByEmailEntity(email: String): UserEntity?
}