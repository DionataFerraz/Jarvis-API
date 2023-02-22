package br.com.jarvis.service.impl

//import org.springframework.security.oauth2.common.OAuth2AccessToken
import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.domain.repository.UserRepository
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.property.FacebookProperties
import br.com.jarvis.service.FacebookLoginService
import br.com.jarvis.service.ScopeTypes
import br.com.jarvis.service.auth.AuthenticationService
import com.restfb.DefaultFacebookClient
import com.restfb.FacebookClient
import com.restfb.Parameter
import com.restfb.Version
import com.restfb.types.User
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
open class FacebookLoginServiceImpl(
    private val userRepository: UserRepository,
    private val facebookProperties: FacebookProperties,
    private val authenticationService: AuthenticationService
) : FacebookLoginService {

    @Transactional
    override fun loginWithFacebook(code: String, scopeType: ScopeTypes): OAuth2AccessToken {
//        val request: SocialLoginRequest = SocialLoginRequest()
//            .setAccessToken(accessToken)
        println("teste ========> secretAppKey1 ${facebookProperties.secretAppKey}")
        val user: User = getFacebookUser(code)
        println("teste ========>$user")
        println("teste ========> birthday ${user.birthday}")
        println("teste ========> name ${user.name}")
        println("teste ========> picture ${user.picture}")
        println("teste ========> id ${user.id}")
        println("teste ========> secretAppKey2 ${facebookProperties.secretAppKey}")
        return createFacebookAccountIfNotExists(user, scopeType)
//        return user
    }


    private fun getFacebookUser(accessToken: String): User {
        val user: User
//        SLF4JBridgeHandler.install()
        user = try {
            val facebookClient: FacebookClient =
                DefaultFacebookClient(accessToken, facebookProperties.secretAppKey, Version.LATEST)
            facebookClient.fetchObject<User>(
                "me",
                User::class.java, Parameter.with("fields", "id, name, email, picture")
            )
        } finally {
//            SLF4JBridgeHandler.uninstall()
        }
        return user
    }

    @Transactional
    private fun createFacebookAccountIfNotExists(
        user: User,
        scopeType: ScopeTypes,
    ): OAuth2AccessToken {
        if (!user.email.isNullOrBlank() && userRepository.existsByEmail(user.email)) {
            println("teste ========> ComicBookNotFoundException")
            throw ComicBookNotFoundException
        }
        val userEntity: UserEntity = findOrCreateFacebookUser(user)

        return authenticationService.authenticateFacebook(userEntity, scopeType)
    }
//
//    @Transactional
//    private fun findOrCreateFacebookUser(user: User): UserEntity {
//        val userEntity = findOrCreateFacebookUser(user)
//        return validateAndUpdateFacebookUserInformation(userEntity)
//    }

    @Transactional
    private fun findOrCreateFacebookUser(user: User): UserEntity {
        println("teste ========> findOrCreateFacebookUser")
        val facebookIdUser: UserEntity? = userRepository.findByTokenFacebook(user.id)
        val finalUser: UserEntity
        if (facebookIdUser != null) {
            finalUser = facebookIdUser
        } else {
            val emailUser: UserEntity? = userRepository.findByEmail(user.email)
            if (emailUser == null) {
                finalUser = createFacebookUser(user)
            } else {
                finalUser = emailUser
            }
        }
        userRepository.save(finalUser)
        println("teste ========> findOrCreateFacebookUser $finalUser")
        return finalUser
    }

    private fun createFacebookUser(user: User): UserEntity {
//        val email = Optional.ofNullable<String>(user.email)
//            .orElseGet {
//                kotlin.String.format(
//                    FacebookLoginServiceImpl.FB_FAKE_EMAIL_FORMAT,
//                    user.id
//                ).lowercase(Locale.getDefault())
//            }
//            .lowercase(Locale.getDefault())
        return UserEntity(
            name = user.name?:"",
            tokenFacebook = user.id,
            email = "open_wquurtr_user@tfbnw.net",
            birthday = null,//user.birthday,
        )
    }


    private fun validateAndUpdateFacebookUserInformation(
        userEntity: UserEntity,
    ): UserEntity {
//        if (userEntity.birthday != null) {
//            userEntity.birthday= userEntity.birthday
//        }
//        if (StringUtils.isEmpty(userEntity.getFacebookId())) {
//            userEntity.setFacebookId(user.id)
//        }
//        if (StringUtils.isEmpty(userEntity.getFacebookProfile())) {
//            userEntity.setFacebookProfile(user.link)
//        }
//        val isUpdatingEmail = StringUtils.isNotEmpty(request.getEmail()) &&
//                !request.getEmail().equals(userEntity.getEmail())
//        if (isUpdatingEmail) {
//            userEntity.setPendingUpdatedEmail(request.getEmail())
//        }
//        updateParentalInformation(userEntity, request)

        val persistedEntity = userRepository.save(userEntity)
        println("teste ========> persistedEntity ${persistedEntity}")
//        if (isUpdatingEmail) {
//            userTokenService.createAndSendEmailChangeToken(persistedEntity)
//        }
        return persistedEntity
    }
}