package br.com.jarvis.config.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfiguration(private val securityFilter: SecurityFilter) {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity, securityFilter: SecurityFilter): SecurityFilterChain {
        return httpSecurity.csrf {
            it.ignoringRequestMatchers("/h2-console/**")
            it.ignoringRequestMatchers(toH2Console())
            it.disable()
        }.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
            it.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            it.requestMatchers(HttpMethod.POST, "/auth/facebook").permitAll()
            it.requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
            it.requestMatchers(HttpMethod.GET, "/api/**").authenticated()
            it.requestMatchers(HttpMethod.POST, "/api/**").authenticated()
        }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**"
            )
        }
    }
}