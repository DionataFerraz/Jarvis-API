package br.com.jarvis.config.security

//import br.com.jarvis.rest.controller.login.CustomOAuth2UserService
import br.com.jarvis.service.auth.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
open class SecurityConfig(
  private val userDetailsService: CustomUserDetailsService
) : WebSecurityConfigurerAdapter() {
    @Bean
    @Throws(java.lang.Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Autowired
    open fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider())
    }


    open fun authProvider(): AuthenticationProvider? {
        return CustomUserDetailsAuthenticationProvider(
            userDetailsService, encoder(),
        )
    }
    @Throws(java.lang.Exception::class)
    override fun configure(http: HttpSecurity) {
//        http
//            .csrf().disable()
//            .anonymous().disable()
//            .authorizeRequests()
//            .antMatchers("/api-public/**").permitAll()
//            .anyRequest().permitAll()
        http
            .csrf()
            .disable()
            .authorizeRequests { requests ->
                requests
                    .antMatchers("/api-public/**")
                    .permitAll()
                    .anyRequest()
                    .permitAll()
            }
            .httpBasic()

    }

    @Bean
    open fun encoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun corsFilter(): FilterRegistrationBean<CorsFilter>? {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        val bean = FilterRegistrationBean(CorsFilter(source))
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }




//    @Bean
//    open fun encoder(): BCryptPasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//
//    @Bean
//    @Throws(Exception::class)
//    override fun authenticationManagerBean(): AuthenticationManager {
//        return super.authenticationManagerBean()
//    }
//
//    @Autowired
//    fun configureGlobal(auth: AuthenticationManagerBuilder) {
//        auth.authenticationProvider(
//            authProvider()
//        )
//    }
//
//    fun authProvider(): AuthenticationProvider {
//        return CustomUserDetailsAuthenticationProvider(
//            userDetailsService, encoder()
//        )
//    }
//
//    @Throws(java.lang.Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http
//            .csrf().disable()
//            .anonymous().disable()
//            .authorizeRequests()
//            .antMatchers("/api-public/**").permitAll()
//            .anyRequest()
//            .authenticated()
//    }
//    @Bean
//    open fun corsFilter(): FilterRegistrationBean<CorsFilter>? {
//        val source = UrlBasedCorsConfigurationSource()
//        val config = CorsConfiguration()
//        config.allowCredentials = true
//        config.addAllowedOrigin("*")
//        config.addAllowedHeader("*")
//        config.addAllowedMethod("*")
//        source.registerCorsConfiguration("/**", config)
//        val bean = FilterRegistrationBean(CorsFilter(source))
//        bean.order = Ordered.HIGHEST_PRECEDENCE
//        return bean
//    }

}

/*@Configuration
@EnableWebSecurity
open class SecurityConfig(
    private val userDetailsService: CustomUserDetailsService
) {

    @Bean
    @Throws(java.lang.Exception::class)
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf()
            .disable()
            .authorizeHttpRequests { requests ->
                requests
                    .antMatchers("/api-public/**")
                    .permitAll()
                    .anyRequest()
                    .permitAll()
            }
            .httpBasic()

        return http.build()
    }


    @Bean
    @Throws(Exception::class)
    open fun authManager(http: HttpSecurity): AuthenticationManager? {
        println("teste ========> authManager")
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService<UserDetailsService>(userDetailsService)
            .and()
            .build()
    }

    @Bean
    open fun encoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun corsFilter(): FilterRegistrationBean<CorsFilter>? {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        val bean = FilterRegistrationBean(CorsFilter(source))
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }

}*/*/*/
