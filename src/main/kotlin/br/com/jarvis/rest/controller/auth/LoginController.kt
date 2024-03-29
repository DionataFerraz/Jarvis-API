package br.com.jarvis.rest.controller.auth

import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.rest.controller.dto.AccessTokenDTO
import br.com.jarvis.service.auth.TokenService
import br.com.jarvis.service.auth.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class LoginController(
    private val service: UserService,
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    // TODO: Tentar reutilizar esse código aqui, pois está duplicado no facebook
    @PostMapping("/login")
    fun login(@Valid @RequestBody loginInput: LoginRequest): ResponseEntity<AccessTokenDTO>? {
        return try {
            val authenticationToken = UsernamePasswordAuthenticationToken(loginInput.email, loginInput.password)
            val authentication: Authentication = authenticationManager.authenticate(authenticationToken)
            val principal = authentication.principal
            val login: UserEntity = principal as UserEntity
            val token = tokenService.generateToken(login)
            ResponseEntity(token, HttpStatus.OK)
        } catch (exception: Exception) {
            throw exception
        }
    }

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody createUserRequest: CreateUserRequest
    ): ResponseEntity<LoginOutput> {
        val loginOutput: LoginOutput = service.createUser(createUserRequest)
        return ResponseEntity(loginOutput, HttpStatus.CREATED)
    }
}
