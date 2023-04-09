package br.com.jarvis.service.auth

import br.com.jarvis.component.security.ScopeAuthenticator
import br.com.jarvis.component.security.TokenGenerator
import br.com.jarvis.config.security.UserDetailsImpl
import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.service.ScopeTypes
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.stereotype.Service

@Service
open class AuthenticationServiceImpl(
    private val scopeAuthenticator: ScopeAuthenticator,
    private val tokenGenerator: TokenGenerator,
) : AuthenticationService {

    override fun authenticateFacebook(userEntity: UserEntity, scopeType: ScopeTypes): OAuth2AccessToken {
        println("teste ========> QUALQUER COISA")
        val userDetailsImpl = UserDetailsImpl(userEntity)
        userDetailsImpl.facebook = true
        return authenticate(userDetailsImpl, scopeType)
    }

    private fun authenticate(userDetailsImpl: UserDetailsImpl, scopeType: ScopeTypes): OAuth2AccessToken {
        val app: String = scopeType.toString().toLowerCase()
        scopeAuthenticator.authenticate(userDetailsImpl, app)
        return tokenGenerator.getToken(userDetailsImpl, app)
    }
}
