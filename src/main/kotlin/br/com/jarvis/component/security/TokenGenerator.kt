package br.com.jarvis.component.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import kotlin.collections.HashSet

@Component
open class TokenGenerator(
    @Lazy private val tokenEnhancer: TokenEnhancer,
//    @Autowired val authTokenServices: AuthorizationServerTokenServices//esse é o problema não encontra a implementação para isso
) {
//    @Autowired lateinit var tokenEnhancer: TokenEnhancer
    @Autowired lateinit var authTokenServices: AuthorizationServerTokenServices
    fun getToken(userDetails: UserDetails, wantedScope: String): OAuth2AccessToken {
        println("teste ========> QUALQUER COISA getToken")
        val authentication = convertAuthentication(userDetails, wantedScope)
        println("teste ========> QUALQUER COISA authentication")
        // A PRINCIPIO O PROBLEMA ESTÁ AQUI, pois a partir daqui ele não executa nada abaixo
        val accessToken = authTokenServices.createAccessToken(authentication)
        println("teste ========> QUALQUER COISA accessToken $accessToken")
        return tokenEnhancer.enhance(accessToken, authentication)
    }

    private fun convertAuthentication(userDetails: UserDetails, wantedScope: String): OAuth2Authentication {
        val scope: MutableSet<String> = HashSet()
        scope.add(wantedScope)
        println("teste ========> QUALQUER COISA getToken wantedScope $wantedScope")
        // mudei aqui o clientid
        val request = OAuth2Request(null,  "jarvis-dev", null, true, scope, null, null, null, null)

        println("teste ========> QUALQUER COISA getToken request $request")

         val oAuth2Authentication = OAuth2Authentication(
            request,
            UsernamePasswordAuthenticationToken(userDetails, "N/A", userDetails.authorities)
        )
        println("teste ========> QUALQUER COISA getToken oAuth2Authentication $oAuth2Authentication")
        return oAuth2Authentication
    }
}