package br.com.jarvis.rest.controller.auth

import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.exception.AuthRuleException
import br.com.jarvis.rest.controller.dto.AccessTokenDTO
import br.com.jarvis.service.auth.FacebookLoginService
import br.com.jarvis.service.auth.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
open class FacebookController(
    private val service: FacebookLoginService,
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    @PostMapping("/facebook")
    fun auth(
        @RequestHeader("access_token") accessToken: String
    ): ResponseEntity<AccessTokenDTO>? {
        return try {
            val user = service.loginWithFacebook(accessToken)// TODO talvez remover essa classe e usar o UserService

            val authenticationToken = UsernamePasswordAuthenticationToken(user.email, "N/A")
            val authentication: Authentication = authenticationManager.authenticate(authenticationToken)
            val principal = authentication.principal
            val login: UserEntity = principal as UserEntity
            val token = tokenService.generateToken(login)
            ResponseEntity(token, HttpStatus.OK)
        } catch (exception: Exception) {
            throw AuthRuleException(exception.message.orEmpty())
        }
    }

}