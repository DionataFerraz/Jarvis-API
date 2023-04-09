package br.com.jarvis.rest.controller.login

import br.com.jarvis.service.FacebookLoginService
import br.com.jarvis.service.ScopeTypes
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api-public/facebook")
open class FacebookController(
    private val service: FacebookLoginService,
) {

    @PostMapping
    fun auth(
        @RequestHeader("access_token") accessToken: String
    ): OAuth2AccessToken {
        return service.loginWithFacebook(accessToken, ScopeTypes.APP)
    }
}



