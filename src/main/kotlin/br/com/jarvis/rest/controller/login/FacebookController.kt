package br.com.jarvis.rest.controller.login

import br.com.jarvis.rest.controller.dto.VolumeDTO
import br.com.jarvis.service.FacebookLoginService
import br.com.jarvis.service.ScopeTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
open class FacebookController(
    private val service: FacebookLoginService,
) {
    @Autowired
    lateinit var tokenServices: DefaultTokenServices

    @PostMapping("/api-public/facebook")
    fun auth(
        @RequestHeader("access_token") accessToken: String
    ): OAuth2AccessToken {
        return service.loginWithFacebook(accessToken, ScopeTypes.APP)
    }

    //isso aqui funciona, então está faltando alguma configuração ou tem algo do spring ferrando
    @PostMapping("/api-public/teste")
    open fun validateToken( @RequestHeader("token") token: String): String? {
        // Valide o token aqui usando o tokenStore e tokenServices
        val authentication: OAuth2Authentication = tokenServices.loadAuthentication(token)
        return if (authentication != null) {
            // Token válido
            "asdasdas $authentication"
        } else {
            // Token inválido
            null
        }
    }
}



