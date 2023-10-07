package br.com.jarvis.config.security


import br.com.jarvis.service.auth.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore


@Configuration
@EnableAuthorizationServer
open class AuthorizationServerConfig(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
) : AuthorizationServerConfigurerAdapter() {

    private val GRANT_TYPE_PASS = "password"
    private val AUTHORIZATION_CODE = "authorization_code"
    private val REFRESH_TOKEN = "refresh_token"
    private val IMPLICIT = "implicit"
    private val ADMIN = "admin"
    private val APP = "app"
    private val SITE = "site"
    private val ACCESS_TOKEN_VALIDITY_SECONDS = 8 * 60 * 60

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient("jarvis-dev") //mudei aqui 524257436231028
            .secret("x*Wg9`xZ\\e@\\^Hd,)rezCW(a=Nfk3qxF") // mudei  aqui o clientsecret a45f53d2c7b335af811797e77d50cbfb
//            .authorizedGrantTypes(GRANT_TYPE_PASS, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
////            .scopes( APP)// SE EU REMOVO ISSO AQUI TODAS AS REQUESTS FUNCIONAM
//            .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
////            .authorizedGrantTypes("authorization_code", "refresh_token")
////            .scopes("read", "write")
//            .accessTokenValiditySeconds(3600) // Token validity time in seconds
    }
//
//    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
//        endpoints.tokenStore(tokenStore())
//    }

    // preciso comentar isso
    @Bean
    @Primary
    open fun defaultTokenServices(): DefaultTokenServices {
        val tokenServices = DefaultTokenServices()
        tokenServices.setTokenStore(tokenStore()) // Set your token store implementation
        return tokenServices
    }

    @Bean
    open fun tokenStore(): TokenStore {
        return InMemoryTokenStore()
    }

    @Bean
    open fun accessTokenConverter(): DefaultAccessTokenConverter? {
        return DefaultAccessTokenConverter()
    }

    @Bean
    open fun tokenEnhancer(): TokenEnhancer? {
        return YourCustomTokenEnhancer()
    }


    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore())
            .tokenEnhancer(tokenEnhancer())
            .accessTokenConverter(accessTokenConverter())
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService)
    }

    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer.checkTokenAccess("permitAll()")
    }

}