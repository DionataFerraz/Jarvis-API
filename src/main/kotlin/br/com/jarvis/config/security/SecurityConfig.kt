package br.com.jarvis.config.security

//import br.com.jarvis.service.auth.CustomUserDetailsService
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.http.HttpMethod
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.oauth2.provider.token.ConsumerTokenServices
//import org.springframework.security.provisioning.InMemoryUserDetailsManager
//import org.springframework.security.web.SecurityFilterChain
//
//
//@Configuration
//@EnableWebSecurity
//open class SecurityConfig(
//    private val userDetailsService: CustomUserDetailsService,
////    private val authProvider: CustomAuthenticationProvider,
//) /*: WebSecurityConfiguration()*/ {
//
////    @Throws(java.lang.Exception::class)
////    open fun configure(web: WebSecurity) {
////        web.ignoring().antMatchers("/api-public/**")
////    }
//
//    @Bean
//    @Throws(java.lang.Exception::class)
//    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//            .csrf()
//            .disable()
//            .authorizeRequests()
//            .antMatchers(HttpMethod.POST, "/api-public/**").permitAll()
////            .authorizeHttpRequests { requests ->
////                requests
////                    .antMatchers("/h2-console/**", "/api-public/**")
////                    .permitAll()
////                    .anyRequest()
////                    .permitAll()
////            }
////            .httpBasic()
//
//        return http.build()
//    }
//
////
////    @Bean
////    @Throws(java.lang.Exception::class)
////    open fun authManager(http: HttpSecurity): AuthenticationManager? {
////        val authenticationManagerBuilder = http.getSharedObject(
////            AuthenticationManagerBuilder::class.java
////        )
////        authenticationManagerBuilder.authenticationProvider(authProvider)
////        return authenticationManagerBuilder.build()
////    }
//
//
//    @Bean
//    @Throws(java.lang.Exception::class)
//    open fun users(): UserDetailsService? {
//        val users: User.UserBuilder = User.withDefaultPasswordEncoder()
//        val manager = InMemoryUserDetailsManager()
//        manager.createUser(users.username("user1").password("password").roles("USER").build())
//        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build())
//        return manager
//    }
//
////    @Bean
////    @Throws(java.lang.Exception::class)
////    override fun authenticationManagerBean(): AuthenticationManager? {
////        return super.authenticationManagerBean()
////    }
//    @Bean
//    @Throws(Exception::class)
//    open fun authManager(http: HttpSecurity): AuthenticationManager? {
//        println("teste ========> authManager")
//        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
//            .userDetailsService<UserDetailsService>(userDetailsService)
//            .and()
//            .build()
//    }
//}


import br.com.jarvis.component.security.CustomRemoteTokenIntrospector
import br.com.jarvis.service.auth.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector


@Configuration
@EnableWebSecurity
open class WebSecurityConfig(
    private val userDetailsService: CustomUserDetailsService,
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {

//        .csrf().disable() se eu coloco isso ele passa todas as requests
//            .antMatchers("/api/**").authenticated()    // Caminho que requer autenticação
        http
            .csrf().disable()
            .cors().and()
//            .csrf().disable()
            .anonymous().disable()
            .authorizeRequests()
            .antMatchers("/api-public/**").permitAll()
            .antMatchers("/api/**").authenticated()
            .anyRequest()
            .authenticated().and().oauth2ResourceServer().opaqueToken()
    }

    @Throws(java.lang.Exception::class)
    override fun configure(web: WebSecurity) {
        web
            .ignoring()
            .antMatchers("/api-public/**") // Caminhos públicos que são ignorados pela segurança
    }
//    open fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }


    @Bean
    open fun opaqueTokenIntrospector(): OpaqueTokenIntrospector? {
        // Configure your OpaqueTokenIntrospector here
        // For example, if using a remote OAuth 2.0 Authorization Server:
        return CustomRemoteTokenIntrospector()
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