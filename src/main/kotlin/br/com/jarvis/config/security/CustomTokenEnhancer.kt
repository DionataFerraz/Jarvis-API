//package br.com.jarvis.config.security
//
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
//import org.springframework.security.oauth2.common.OAuth2AccessToken
//import org.springframework.security.oauth2.provider.OAuth2Authentication
//import org.springframework.security.oauth2.provider.token.TokenEnhancer
//
///**
// * @author ahron.helfenstein
// */
//class CustomTokenEnhancer : TokenEnhancer {
//
//    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
//        val additionalInfo: MutableMap<String, Any?> = HashMap()
//        val user = authentication.getPrincipal() as MainUser
//        additionalInfo["username"] = user.username
//        additionalInfo["name"] = user.name
//        additionalInfo["id"] = user.id
//        (accessToken as DefaultOAuth2AccessToken).setAdditionalInformation(additionalInfo)
//        return accessToken
//    }
//}