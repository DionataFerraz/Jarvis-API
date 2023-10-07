package br.com.jarvis.config.security

import org.springframework.stereotype.Component
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import java.util.HashMap

@Component
open class YourCustomTokenEnhancer : TokenEnhancer {

    override fun enhance(accessToken: OAuth2AccessToken?, authentication: OAuth2Authentication?): OAuth2AccessToken {
        val userDetails = authentication?.principal as UserDetailsImpl
        val additionalInfo = HashMap<String, Any>()
        additionalInfo["user_id"] = userDetails.id ?: "" // Substitua pelo ID do usuário real
        additionalInfo["user_facebook"] = userDetails.tokenFacebook ?: "" // Substitua pelo ID do usuário real
//        additionalInfo["scope"] = userDetails.scope ?: "" // Substitua pelo ID do usuário real

        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo
        return accessToken
    }
}