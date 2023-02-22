package br.com.jarvis.component.security

import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.stereotype.Service

@Service
//@ConfigurationProperties(value = "spring.security.oauth2.client")
open class TokenGenerator(
    @Lazy private val tokenEnhancer: TokenEnhancer,
    @Lazy private val authTokenServices: AuthorizationServerTokenServices//esse é o problema
) {
    fun getToken(userDetails: UserDetails, wantedScope: String): OAuth2AccessToken {
        println("teste ========> QUALQUER COISA getToken")
        val authentication = convertAuthentication(userDetails, wantedScope)
        val accessToken = authTokenServices.createAccessToken(authentication)
        return tokenEnhancer.enhance(accessToken, authentication)
    }

    private fun convertAuthentication(userDetails: UserDetails, wantedScope: String): OAuth2Authentication {
        val scope: MutableSet<String> = HashSet()
        scope.add(wantedScope)
        val request = OAuth2Request(null, "jarvis-dev", null, true, scope, null, null, null, null)
        return OAuth2Authentication(
            request,
            UsernamePasswordAuthenticationToken(userDetails, "N/A", userDetails.authorities)
        )
    }
}