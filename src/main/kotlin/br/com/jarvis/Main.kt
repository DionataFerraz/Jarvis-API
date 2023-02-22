package br.com.jarvis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@SpringBootApplication
open class MainApplication{

    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.setAllowedOrigins(listOf("http://localhost:8080"))
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/api**", configuration)
        return source
    }
}

fun main(args: Array<String>) {
    runApplication<MainApplication>(*args)
}