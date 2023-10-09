package br.com.jarvis.service.auth

import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.rest.controller.dto.AccessTokenDTO
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService(@Value("\${api.security.token.secret}") private val secret: String = "bolinha-de-meu-deus") {

    private val algorithm = Algorithm.HMAC512(secret)

    fun generateToken(user: UserEntity): AccessTokenDTO {
        return try {
            AccessTokenDTO(
                accessToken = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.email)
                    .withClaim("userId", user.id)
                    .withClaim("role", user.roleType.name)
                    .withClaim("email", user.email)
                    .withExpiresAt(generateOneHourExpirationUTC())
                    .sign(this.algorithm)
            )
        } catch (exception: JWTCreationException) {
            throw RuntimeException("Error while generating token", exception)
        }
    }

    fun validateToken(token: String): String {
        return try {
            JWT.require(this.algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .subject
        } catch (jwtVerificationException: JWTVerificationException) {
            throw RuntimeException(jwtVerificationException);
        }
    }

    private fun generateOneHourExpirationUTC(): Instant =
        LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))

}