package br.com.jarvis.rest.controller.auth

//import br.com.jarvis.config.security.TokenService
//import br.com.jarvis.domain.entity.UserEntity
//import br.com.jarvis.service.auth.LoginService
//import jakarta.validation.Valid
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.Authentication
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//
//@RestController
//@RequestMapping("/api-public")
//open class LoginController(
//    private val service: LoginService,
//    private val authenticationManager: AuthenticationManager,
//    private val tokenService: TokenService,
//) {
//
//    @PostMapping("/login")
//    fun auth(
//        @RequestBody loginRequest: @Valid LoginRequest,
//    ): String? {
//        return null
//    }
//
//    @PostMapping("/login")
//    open fun login(@RequestBody loginInput: @Valid LoginRequest?): ResponseEntity<String>? {
//        return try {
//            val userAuthDTO = UsernamePasswordAuthenticationToken(loginInput?.email, loginInput?.password)
//            val authentication: Authentication = authenticationManager.authenticate(userAuthDTO)
//            val principal = authentication.principal
//            val login: UserEntity = principal as UserEntity
//            val token: String = tokenService.generateToken(login)
//            ResponseEntity(token, HttpStatus.OK)
//        } catch (e: Exception) {
//            ResponseEntity("Verifique suas credenciais!", HttpStatus.FORBIDDEN)
//        }
//    }
//
//
//
//    @PostMapping("/register")
//    fun register(
//        @RequestBody loginRequest: @Valid LoginCreateRequest,
//    ): ResponseEntity<LoginOutput> {
//        val loginOutput = service.createUser(loginRequest)
//        return ResponseEntity<LoginOutput>(loginOutput, HttpStatus.CREATED)
//    }
//
//}
