package br.com.jarvis.rest.controller.login.social

import br.com.jarvis.rest.controller.login.LoginCreateRequest
import br.com.jarvis.rest.controller.login.LoginOutput
import br.com.jarvis.rest.controller.login.LoginRequest
import br.com.jarvis.service.login.emailpassword.LoginService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api-public")
open class FacebookController(
//    private val service: FacebookLoginService,
) {

//    @PostMapping("/facebook")
//    fun auth(
//        @RequestHeader("access_token") accessToken: String
//    ): OAuth2AccessToken {
//        return service.loginWithFacebook(accessToken, ScopeTypes.APP)
//    }

//    @PostMapping
//    fun auth(
//        @RequestHeader("access_token") accessToken: String
//    ): String {
//        return "Abacaxi"
//    }

}



