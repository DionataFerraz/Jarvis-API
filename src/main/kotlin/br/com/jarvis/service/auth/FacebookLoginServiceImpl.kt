package br.com.jarvis.service.auth

import br.com.jarvis.FacebookProperties
import br.com.jarvis.domain.entity.RoleType
import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.domain.repository.UserRepository
import br.com.jarvis.exception.AuthRuleException
import com.restfb.DefaultFacebookClient
import com.restfb.FacebookClient
import com.restfb.Parameter
import com.restfb.Version
import com.restfb.types.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// TODO: Ajustar esse macarrão de código
@Service
open class FacebookLoginServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val facebookProperties: FacebookProperties,
) : FacebookLoginService {

    @Transactional
    override fun loginWithFacebook(code: String): UserEntity {
        val user: User = getFacebookUser(code)
        return createFacebookAccountIfNotExists(user)
    }

    private fun getFacebookUser(accessToken: String): User {
        val user: User = try {
            val facebookClient: FacebookClient =
                DefaultFacebookClient(accessToken, facebookProperties.secretAppKey, Version.LATEST)
            facebookClient.fetchObject(
                "me",
                User::class.java, Parameter.with("fields", "id, name, email, picture")
            )
        } catch (ex: Exception) {
            throw AuthRuleException("Error when trying to log in with Facebook: ${ex.message}")
        }
        return user
    }

    @Transactional
    private fun createFacebookAccountIfNotExists(
        user: User,
    ): UserEntity {
        if (!user.email.isNullOrBlank() && userRepository.existsByEmail(user.email)) {
            throw UsernameNotFoundException("User not found with email: ${user.email}")
        }

        return findOrCreateFacebookUser(user)
//        return authenticationService.authenticateFacebook(userEntity)
    }


    @Transactional
    private fun findOrCreateFacebookUser(user: User): UserEntity {
        println("teste ========> findOrCreateFacebookUser $user")
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
            pass = passwordEncoder.encode("N/A"),//user.id
            birthday = null,//user.birthday,
            roleType = RoleType.APP,
        )
    }
}