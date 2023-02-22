package br.com.jarvis.config.security

import br.com.jarvis.exception.ComicBookExistsException
import br.com.jarvis.exception.ComicBookNeedsImageTypeException
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.service.ScopeTypes
import br.com.jarvis.service.auth.CustomUserDetailsService
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.Assert

class CustomUserDetailsAuthenticationProvider(
    private val userDetailsService: CustomUserDetailsService,
    private val passwordEncoder: PasswordEncoder,
) : AbstractUserDetailsAuthenticationProvider() {

    private var userNotFoundEncodedPassword: String? = null

    override fun additionalAuthenticationChecks(
        userDetails: UserDetails,
        authentication: UsernamePasswordAuthenticationToken
    ) {
        val request: String = authentication.getDetails().toString()

        val app: String = ScopeTypes.APP.toString().toLowerCase()
        val site: String = ScopeTypes.SITE.toString().toLowerCase()
        val admin: String = ScopeTypes.ADMIN.toString().toLowerCase()

    }

    override fun doAfterPropertiesSet() {
        Assert.notNull(userDetailsService, "A UserDetailsService must be set")
        userNotFoundEncodedPassword = passwordEncoder?.encode(USER_NOT_FOUND_PASS)
    }

    override fun retrieveUser(
        username: String,
        authentication: UsernamePasswordAuthenticationToken
    ): MainUser {
        if (authentication.getPrincipal() == null) {
            throw ComicBookNotFoundException
        }
//        val loadedUser = loadUser(authentication)
        val loadedUser = userDetailsService.loadUserByUsernameOrEmail(
            (authentication.getPrincipal() as MainUser).email,
        )
//        println("teste ========> retrieveUser getPrincipal ${authentication.getPrincipal()}")
//        println("teste ========> retrieveUser loadedUser ${loadedUser}")

//        if (loadedUser?.username == null && !loadedUser?.facebook) {
//            throw ComicBookExistsException
//        }
        if (loadedUser == null){
            throw ComicBookExistsException
        }
        println("teste ========> retrieveUser getPrincipal ${authentication.getPrincipal()}")
        println("teste ========> retrieveUser loadedUser ${loadedUser}")


        // TODO ver na próxima sprint
        //loginLogService.log(loadedUser.getId());
        return loadedUser

    }

    /*private fun loadUser(authentication: UsernamePasswordAuthenticationToken): MainUser {
        return try {
            val loginType: LoginType = LoginType.from(authentication)
            loadUserByLoginType(authentication, loginType)
        } catch (notFound: ComicBookNeedsImageTypeException) {
            if (authentication.getCredentials() != null) {
                val presentedPassword: String = authentication.getCredentials()
                    .toString()
                passwordEncoder?.matches(presentedPassword, userNotFoundEncodedPassword)
            }
            throw notFound
        } catch (repositoryProblem: Exception) {
            throw  ComicBookExistsException
        }
    }*/

/*    private fun loadUserByLoginType(
        authentication: UsernamePasswordAuthenticationToken,
        loginType: LoginType
    ): MainUser {
        val loadedUser: MainUser = loadFacebookUser(authentication)
//        loadedUser = when (loginType) {
//            LoginType.FACEBOOK ->
////            LoginType.GOOGLE -> loadGoogleUser(authentication)
////            LoginType.ACCESS_CODE -> userDetailsService?.loadUserByUsernameOrEmail(
////                (authentication.getPrincipal() as MainUser).email,
////                LoginType.ACCESS_CODE
////            )!!
//            else -> userDetailsService.loadUserByUsernameOrEmail(
//                authentication.getPrincipal().toString(),
//            )!!
//        }
        return loadedUser
    }*/

  /*  private fun loadFacebookUser(authentication: UsernamePasswordAuthenticationToken): MainUser {
        return if ((authentication.getPrincipal() as MainUser).email != null) {
            userDetailsService.loadUserByUsernameOrEmail(
                (authentication.getPrincipal() as MainUser).email,
            )!!
        } else {
            userDetailsService.loadUserByFacebookName((authentication.getPrincipal() as MainUser).name)!!
        }
    }*/

    companion object {
        private const val USER_NOT_FOUND_PASS = "userNotFoundPassword"
    }
}