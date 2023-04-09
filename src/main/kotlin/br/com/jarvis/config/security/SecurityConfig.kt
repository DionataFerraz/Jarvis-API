package br.com.jarvis.config.security

import br.com.jarvis.service.auth.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
open class SecurityConfig(
    private val userDetailsService: CustomUserDetailsService,
//    private val authProvider: CustomAuthenticationProvider,
) /*: WebSecurityConfiguration()*/ {

//    @Throws(java.lang.Exception::class)
//    open fun configure(web: WebSecurity) {
//        web.ignoring().antMatchers("/api-public/**")
//    }

    @Bean
    @Throws(java.lang.Exception::class)
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf()
            .disable()
            .authorizeHttpRequests { requests ->
                requests
                    .antMatchers("/h2-console/**", "/api-public/**")
                    .permitAll()
                    .anyRequest()
                    .permitAll()
            }
            .httpBasic()

        return http.build()
    }

//
//    @Bean
//    @Throws(java.lang.Exception::class)
//    open fun authManager(http: HttpSecurity): AuthenticationManager? {
//        val authenticationManagerBuilder = http.getSharedObject(
//            AuthenticationManagerBuilder::class.java
//        )
//        authenticationManagerBuilder.authenticationProvider(authProvider)
//        return authenticationManagerBuilder.build()
//    }

    @Bean
    @Throws(Exception::class)
    open fun authManager(http: HttpSecurity): AuthenticationManager? {
        println("teste ========> authManager")
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService<UserDetailsService>(userDetailsService)
            .and()
            .build()
    }
}

