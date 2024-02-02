package br.com.jarvis.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authorization.AuthorityAuthorizationManager
import org.springframework.security.authorization.AuthorizationManagers
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
            it.disable()
        }.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/facebook").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/v1/home").hasAnyAuthority("ADMIN", "APP").anyRequest().authenticated()
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