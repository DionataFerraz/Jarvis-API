package br.com.jarvis.service

import com.restfb.types.User
import org.springframework.security.oauth2.common.OAuth2AccessToken

interface FacebookLoginService {

    fun loginWithFacebook(code: String, scopeType: ScopeTypes): OAuth2AccessToken
}