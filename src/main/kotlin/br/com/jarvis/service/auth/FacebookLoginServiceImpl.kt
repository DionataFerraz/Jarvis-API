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
    }


    @Transactional
    private fun findOrCreateFacebookUser(user: User): UserEntity {
        val finalUser: UserEntity =
            userRepository.findByTokenFacebook(user.id) ?: (userRepository.findByName(user.name) ?: createFacebookUser(
                user
            ))

        userRepository.save(finalUser)
        return finalUser
    }

    private fun createFacebookUser(user: User): UserEntity {
        return UserEntity(
            name = user.name ?: "",
            tokenFacebook = user.id,
            email = user.email,
            pass = passwordEncoder.encode("N/A"),
            birthday = null,//user.birthday,
            roleType = RoleType.APP,
        )
    }
}