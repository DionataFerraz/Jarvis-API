package br.com.jarvis.service.impl

import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.domain.repository.UserRepository
import br.com.jarvis.property.FacebookProperties
import br.com.jarvis.service.FacebookLoginService
import br.com.jarvis.service.ScopeTypes
import br.com.jarvis.service.auth.AuthenticationService
import com.restfb.DefaultFacebookClient
import com.restfb.FacebookClient
import com.restfb.Parameter
import com.restfb.Version
import com.restfb.exception.FacebookException
import com.restfb.types.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class FacebookLoginServiceImpl(
    private val userRepository: UserRepository,
    private val facebookProperties: FacebookProperties,
    private val authenticationService: AuthenticationService
) : FacebookLoginService {

    @Transactional
    override fun loginWithFacebook(code: String, scopeType: ScopeTypes): OAuth2AccessToken {
        val user: User = getFacebookUser(code)
        return createFacebookAccountIfNotExists(user, scopeType)
    }

//    private fun getFacebookUser(accessToken: String): User {
//        try {
//            val facebookClient: FacebookClient =
//                DefaultFacebookClient(accessToken, facebookProperties.secretAppKey, Version.LATEST)
//            return facebookClient.fetchObject<User>(
//                "me",
//                User::class.java, Parameter.with("fields", "id, name, email, picture")
//            )
//        } catch (exception: FacebookException) {
//            throw UsernameNotFoundException("User not found on facebook or token expired ${exception.message}")
//        }
//    }
    private fun getFacebookUser(accessToken: String): User {
        val user: User
        user = try {
            val facebookClient: FacebookClient =
                DefaultFacebookClient(accessToken, facebookProperties.secretAppKey, Version.LATEST)
            facebookClient.fetchObject<User>(
                "me",
                User::class.java, Parameter.with("fields", "id, name, email, picture")
            )
        } finally {

        }
        return user
    }

    @Transactional
    private fun createFacebookAccountIfNotExists(
        user: User,
        scopeType: ScopeTypes,
    ): OAuth2AccessToken {
        if (!user.email.isNullOrBlank() && userRepository.existsByEmail(user.email)) {
            throw UsernameNotFoundException("User not found with email: ${user.email}")
        }

        val userEntity: UserEntity = findOrCreateFacebookUser(user)
        return authenticationService.authenticateFacebook(userEntity, scopeType)
    }


    @Transactional
    private fun findOrCreateFacebookUser(user: User): UserEntity {
        println("teste ========> findOrCreateFacebookUser")
        val facebookIdUser: UserEntity? = userRepository.findByTokenFacebook(user.id)
        val finalUser: UserEntity
        if (facebookIdUser != null) {
            println("teste ========> findOrCreateFacebookUser facebookIdUser != null")
            finalUser = facebookIdUser
        } else {
            println("teste ========> findOrCreateFacebookUser else")
            val emailUser: UserEntity? = userRepository.findByName(user.name)
            println("teste ========> findOrCreateFacebookUser else emailUser $emailUser")
            if (emailUser == null) {
                finalUser = createFacebookUser(user)
            } else {
                finalUser = emailUser
            }
        }

        userRepository.save(finalUser)
        println("teste ========> findOrCreateFacebookUser save $finalUser")
        return finalUser
    }

    private fun createFacebookUser(user: User): UserEntity {
        return UserEntity(
            name = user.name ?: "",
            tokenFacebook = user.id,
            email = user.name, // caso eu mude para email ele não funciona, fica dando invalid login details no CustomAuthenticationProvider
            password = "password",
            birthday = null,//user.birthday,
        )
    }
}