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
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import java.util.*
import kotlin.collections.HashSet

@Service
//@ConfigurationProperties(value = "spring.security.oauth2.client")
//@ConfigurationProperties(value = "spring.social.facebook")
open class TokenGenerator(
//    @Value("\${spring.security.oauth2.client.clientId}")

//    @JsonProperty("client-id")private val clientId: String,
    @Lazy private val tokenEnhancer: TokenEnhancer,
    @Lazy private val authTokenServices: AuthorizationServerTokenServices//esse é o problema
) {
//    private lateinit var tokenEnhancer: TokenEnhancer
//    private lateinit var  authTokenServices: AuthorizationServerTokenServices
//    constructor(tokenEnhancer: TokenEnhancer, authTokenServices: AuthorizationServerTokenServices) : this() {
//        this.tokenEnhancer = tokenEnhancer
//        this.authTokenServices = authTokenServices
//    }




    fun getToken(userDetails: UserDetails, wantedScope: String): OAuth2AccessToken {
        println("teste ========> QUALQUER COISA getToken")
        val authentication = convertAuthentication(userDetails, wantedScope)
        println("teste ========> QUALQUER COISA authentication")
        val accessToken = authTokenServices.createAccessToken(authentication) /// A PRINCIPIO O PROBLEMA ESTÁ AQUI
        println("teste ========> QUALQUER COISA accessToken $accessToken")
        return tokenEnhancer.enhance(accessToken, authentication)
    }

    private fun convertAuthentication(userDetails: UserDetails, wantedScope: String): OAuth2Authentication {
        val scope: MutableSet<String> = HashSet()
        scope.add(wantedScope)
        println("teste ========> QUALQUER COISA getToken wantedScope $wantedScope")
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