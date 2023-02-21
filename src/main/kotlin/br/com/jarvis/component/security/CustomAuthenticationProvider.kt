//package br.com.jarvis.component.security
//
//import br.com.jarvis.config.security.MainUser
//import br.com.jarvis.exception.ComicBookExistsException
//import br.com.jarvis.service.auth.CustomUserDetailsService
//import org.springframework.security.authentication.AuthenticationProvider
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.AuthenticationException
//import org.springframework.stereotype.Component
//
//
//@Component
//class CustomAuthenticationProvider(
//    private val userDetailsService: CustomUserDetailsService
//) : AuthenticationProvider {
//
//    @Throws(AuthenticationException::class)
//    override fun authenticate(authentication: Authentication): Authentication? {
//        val name: String = authentication.getName()
//        val password: String = authentication.getCredentials().toString()
//
//
//        println("teste ========> CustomAuthenticationProvider $name")
//        println("teste ========> CustomAuthenticationProvider $password")
//
//
//        val loadedUser = userDetailsService.loadUserByUsernameOrEmail(
//            (authentication.getPrincipal() as MainUser).email,
//        )
////        println("teste ========> retrieveUser getPrincipal ${authentication.getPrincipal()}")
////        println("teste ========> retrieveUser loadedUser ${loadedUser}")
//
////        if (loadedUser?.username == null && !loadedUser?.facebook) {
////            throw ComicBookExistsException
////        }
//        if (loadedUser == null){
//            throw ComicBookExistsException
//        }
//
//        return UsernamePasswordAuthenticationToken(
//            name, password, ArrayList()
//        )
//        /* return if (shouldAuthenticateAgainstThirdPartySystem()) {
//
//             // use the credentials
//             // and authenticate against the third-party system
//             UsernamePasswordAuthenticationToken(
//                 name, password, ArrayList()
//             )
//         } else {
//             null
//         }*/
//    }
//
//    override fun supports(authentication: Class<*>): Boolean {
//        return authentication == UsernamePasswordAuthenticationToken::class.java
//    }
//}