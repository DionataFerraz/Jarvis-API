package br.com.jarvis.rest.controller.login

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User


class CustomOAuth2User(oauth2User: OAuth2User) : OAuth2User {
    private val oauth2User: OAuth2User

    init {
        this.oauth2User = oauth2User
    }

//    val attributes: Map<String, Any>
//        get() = oauth2User.getAttributes()
//    val authorities: Collection<Any?>
//        get() = oauth2User.getAuthorities()
//    val name: String
//        get() = oauth2User.getAttribute("name")
//    val email: String
//        get() = oauth2User.getAttribute("email")

    override fun getName(): String? {
       return oauth2User.getAttribute("name")
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return oauth2User.attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
       return oauth2User.authorities
    }

    fun getEmail(): String? {
        return oauth2User.getAttribute("email")
    }
}
