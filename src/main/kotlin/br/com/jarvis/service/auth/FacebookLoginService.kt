package br.com.jarvis.service.auth

import br.com.jarvis.domain.entity.UserEntity

interface FacebookLoginService {
    fun loginWithFacebook(code: String): UserEntity
}