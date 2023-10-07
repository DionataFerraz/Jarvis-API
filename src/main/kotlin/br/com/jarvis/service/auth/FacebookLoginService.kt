package br.com.jarvis.service.auth

import br.com.jarvis.domain.entity.RoleType
import org.springframework.security.oauth2.common.OAuth2AccessToken

interface FacebookLoginService {
    fun loginWithFacebook(code: String, scopeType: RoleType): OAuth2AccessToken
}